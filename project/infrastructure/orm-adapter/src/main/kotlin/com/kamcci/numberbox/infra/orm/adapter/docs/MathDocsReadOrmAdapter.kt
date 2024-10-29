package com.kamcci.numberbox.infra.orm.adapter.docs

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.port.repository.docs.MathDocsReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.querydsl.core.types.dsl.Expressions
import org.springframework.stereotype.Repository

@Repository
class MathDocsReadOrmAdapter : MathDocsReadOrmPort, BaseRepository() {
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

    override fun findPartitionedByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>,
        countByType: Int,
        limit: Int
    ): List<Any> {
        // 결과를 매핑할 DTO 정의 (예: ResultDTO)
        val mysqlQuery = em.createNativeQuery(
            """
            SELECT 
                A.contents_no AS contentsNo, 
                A.unit_uniq_no AS unitUniqNo, 
                A.type_no AS typeNo, 
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
                C.ques_type
            FROM (
                SELECT *, 
                       ROW_NUMBER() OVER (PARTITION BY unit_uniq_no, type_no ORDER BY RAND()) AS row_num
                FROM math_contents 
                WHERE contents_classify = :contentsClassify
                  AND svc_posb_stts = :svcPosbStts
                  AND ques_level IN (:quesLv)
                  AND CONCAT(unit_uniq_no, ',', type_no) IN (:unitIdAndTypeId)
            ) AS A
            JOIN math_unit_info AS B ON A.unit_uniq_no = B.unit_uniq_no
            JOIN math_type_info AS C ON A.unit_uniq_no = C.unit_uniq_no AND A.type_no = C.type_no
            WHERE A.row_num <= :countByType
            LIMIT :limit
        """,
            Any::class.java
        )
        mysqlQuery.setParameter("contentsClassify", contentsClassifyType)
        mysqlQuery.setParameter("svcPosbStts", ContentsSvcPosbSttsType.Release.id)
        mysqlQuery.setParameter("quesLv", quesLv)
        mysqlQuery.setParameter("unitIdAndTypeId", unitIdAndTypeId)
        mysqlQuery.setParameter("countByType", countByType)
        mysqlQuery.setParameter("limit", limit)


        return mysqlQuery.resultList as List<Any>
    }
}