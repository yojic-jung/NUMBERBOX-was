package com.kamcci.numberbox.infra.orm.adapter.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.app.domain.vo.docs.MathInHouseDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo
import com.kamcci.numberbox.app.port.repository.docs.MathDocsReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.MathTypeDomain
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsIpsiSrcEntity.mathContentsIpsiSrcEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathTypeInfoEntity.mathTypeInfoEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathUnitInfoEntity.mathUnitInfoEntity
import com.kamcci.numberbox.infra.orm.util.docs.MathDocsExpression
import com.querydsl.core.types.dsl.Expressions
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class MathDocsReadOrmAdapter(
    private val mathDocsExpression: MathDocsExpression
) : MathDocsReadOrmPort, BaseRepository() {
    override fun countGroupByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>
    ): List<Long> {
        return queryFactory
            .select(mathContentsEntity.id.count())
            .from(mathContentsEntity)
            .where(
                mathContentsEntity.svcPosbStts.eq(ContentsSvcPosbSttsType.Release),
                mathContentsEntity.contentsClassify.eq(contentsClassifyType),
                mathContentsEntity.quesLevel.`in`(quesLv),
                Expressions.stringTemplate(
                    "CONCAT({0}, ',', {1})",
                    mathContentsEntity.unitId,
                    mathContentsEntity.typeId
                ).`in`(unitIdAndTypeId)
            ).fetch()
    }

    override fun readAllInHouseDocsVoBy(
        unitIdAndTypeId: List<String>,
        quesLv: List<Int>,
        countByType: Int,
        limit: Int
    ): List<MathInHouseDocsVo> {
        // 결과를 매핑할 DTO 정의 (예: ResultDTO)
        val mysqlQuery = em.createNativeQuery(
            """
            SELECT 
               CAST(A.contents_no AS UNSIGNED) AS contentsId, 
                A.unit_uniq_no AS unitId, 
                A.type_no AS typeId, 
                A.contents, 
                A.contents_img, 
                A.img_path, 
                A.solution, 
                A.solution_img, 
                A.solution_img_path, 
                A.fir_no, 
                A.sec_no, 
                A.thr_no, 
                A.four_no, 
                A.fif_no, 
                A.multi_choice_type, 
                A.answer, 
                A.choice_answer, 
                A.ques_level, 
                A.ans_exist_stts, 
                A.contents_classify, 
                B.subject, 
                B.fir_unit, 
                B.sec_unit, 
                B.thr_unit, 
                C.ques_type,
                A.sys_create_date
            FROM (
                SELECT *, 
                       ROW_NUMBER() OVER (PARTITION BY unit_uniq_no, type_no ORDER BY RAND()) AS row_num
                FROM math_contents 
                WHERE contents_classify = :contentsClassify
                  AND svc_posb_stts = :svcPosbStts
                  AND ques_level IN (:quesLv)
                  AND CONCAT(type_no, '-',unit_uniq_no ) IN (:unitIdAndTypeId)
            ) AS A
            JOIN math_unit_info AS B ON A.unit_uniq_no = B.unit_uniq_no
            JOIN math_type_info AS C ON A.unit_uniq_no = C.unit_uniq_no AND A.type_no = C.type_no
            WHERE A.row_num <= :countByType
            LIMIT :limit
        """,
            Any::class.java
        )
        mysqlQuery.setParameter("contentsClassify", ContentsClassifyType.InHouse)
        mysqlQuery.setParameter("svcPosbStts", ContentsSvcPosbSttsType.Release.id)
        mysqlQuery.setParameter("quesLv", quesLv)
        mysqlQuery.setParameter("unitIdAndTypeId", unitIdAndTypeId)
        mysqlQuery.setParameter("countByType", countByType)
        mysqlQuery.setParameter("limit", limit)
        val resultList = mysqlQuery.resultList.map {
            val result = it as Array<out Any>
            for (idx in 0..result.size - 1) {
                println("$idx : ${result[idx]}")
            }
            MathInHouseDocsVo(
                contentsId = result[0] as Long,
                unitId = result[1] as Int,
                typeId = result[2] as Int,
                contents = result[3] as String,
                contentsImg = result[4] as String?,
                imgPath = result[5] as String?,
                solution = result[6] as String?,
                solutionImg = result[7] as String?,
                solutionImgPath = result[8] as String?,
                firNo = result[9] as String?,
                secNo = result[10] as String?,
                thrNo = result[11] as String?,
                fourNo = result[12] as String?,
                fifNo = result[13] as String?,
                multiChoiceType = MultiChoiceType.entries.find { it.id == result[14].toString() } as MultiChoiceType,
                answer = result[15] as String?,
                choiceAnswer = result[16] as String?,
                quesLevel = result[17] as Int,
                ansExistStts = result[18].toString() == "1",
                contentsClassify = ContentsClassifyType.entries.find { it.id.toString() == result[19].toString() } as ContentsClassifyType,
                subject = result[20] as String,
                firUnit = result[21] as String,
                secUnit = result[22] as String,
                thrUnit = result[23] as String,
                quesType = result[24] as String,
                sysCreateDate = (result[25] as Timestamp).toLocalDateTime()
            )
        }

        return resultList
    }

    override fun readAllIpsiDocsVoBy(
        readDto: MathIpsiDocsReadDto
    ): List<MathIpsiDocsVo> {
        return queryFactory
            .select(mathDocsExpression.ceMathIpsiDocsVo())
            .from(mathContentsEntity)
            .innerJoin(mathUnitInfoEntity)
            .on(mathContentsEntity.unitId.eq(mathUnitInfoEntity.id))
            .innerJoin(mathTypeInfoEntity)
            .on(
                mathContentsEntity.unitId.eq(mathTypeInfoEntity.mathTypeDomain.unitId),
                mathContentsEntity.typeId.eq(mathTypeInfoEntity.mathTypeDomain.typeId),
            )
            .innerJoin(mathContentsEntity.mathContentsIpsiSrc, mathContentsIpsiSrcEntity)
            .where(
                mathContentsEntity.svcPosbStts.eq(ContentsSvcPosbSttsType.Release),
                mathContentsEntity.contentsClassify.eq(ContentsClassifyType.Ipsi),
                // 검색 조건
                Expressions.stringTemplate(
                    "CONCAT({0}, '-', {1})",
                    mathContentsEntity.typeId,
                    mathContentsEntity.unitId
                ).`in`(readDto.unitIdAndTypeId),
                mathContentsEntity.quesLevel.`in`(readDto.quesLevel),
                mathContentsIpsiSrcEntity.wrongRatio.between(readDto.wrongRatioMin, readDto.wrongRatioMax),
                mathContentsIpsiSrcEntity.impYear.between(readDto.ipsiYearStrt, readDto.ipsiYearEnd)
            )
            .limit(readDto.count)
            .fetch()
    }

    override fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathInHouseDocsVo> {
        val mathTypeDomain = MathTypeDomain(readDto.unitId, readDto.typeId)
        return queryFactory
            .select(mathDocsExpression.ceMathInHouseDocsVo())
            .from(mathContentsEntity)
            .innerJoin(mathUnitInfoEntity)
            .on(mathContentsEntity.unitId.eq(mathUnitInfoEntity.id))
            .innerJoin(mathTypeInfoEntity)
            .on(
                mathContentsEntity.unitId.eq(mathTypeInfoEntity.mathTypeDomain.unitId),
                mathContentsEntity.typeId.eq(mathTypeInfoEntity.mathTypeDomain.typeId),
            )
            .where(
                mathContentsEntity.svcPosbStts.eq(ContentsSvcPosbSttsType.Release),
                mathContentsEntity.contentsClassify.eq(readDto.contentsClassifyType),
                mathTypeInfoEntity.mathTypeDomain.eq(mathTypeDomain)
            )
            .orderBy(mathContentsEntity.id.desc())
            .fetch()
    }
}