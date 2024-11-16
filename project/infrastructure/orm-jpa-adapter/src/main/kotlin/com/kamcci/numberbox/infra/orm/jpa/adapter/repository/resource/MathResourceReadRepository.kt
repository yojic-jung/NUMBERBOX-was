package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceCateVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceDetailVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceImgVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceCateEntity.mathResourceCateEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceEntity.mathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceImgEntity.mathResourceImgEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.resource.MathResourceExpression
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathResourceReadRepository(
    private val mathResourceExpression: MathResourceExpression
) : MathResourceReadOrmPort, BaseRepository() {
    override fun readByMainCateId(mainCateId: Int, pageReq: PageRequest): List<MathResourceVo> =
        queryFactory
            .select(mathResourceExpression.ceMathResourceVo())
            .from(mathResourceEntity)
            .innerJoin(mathResourceEntity.mathResourceCate, mathResourceCateEntity)
            .where(mathResourceCateEntity.mainCateId.eq(mainCateId))
            .offset(pageReq.getOffset())
            .limit(pageReq.pageVolume)
            .fetch()

    override fun countByMainCateId(mainCateId: Int): Long =
        queryFactory
            .select(mathResourceEntity.id.count())
            .from(mathResourceEntity)
            .innerJoin(mathResourceEntity.mathResourceCate, mathResourceCateEntity)
            .where(mathResourceCateEntity.mainCateId.eq(mainCateId))
            .fetchFirst()

    override fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathResourceDetailVo> {
        // 수학 자료
        val resources =
            queryFactory
                .selectFrom(mathResourceEntity)
                .where(mathResourceEntity.memberId.eq(memberId))
                .orderBy(mathResourceEntity.id.desc())
                .offset(pageReq.getOffset())
                .limit(pageReq.pageVolume)
                .fetch()

        val resourceIds = resources.map { it.id }

        // 수학 자료 카테고리
        val resourceCate = queryFactory
            .selectFrom(mathResourceCateEntity)
            .where(mathResourceCateEntity.mathResource.id.`in`(resourceIds))
            .fetch()

        // 수학 자료 카테고리
        val resourceImg = queryFactory
            .selectFrom(mathResourceImgEntity)
            .where(mathResourceImgEntity.mathResource.id.`in`(resourceIds))
            .fetch()

        // Vo로 변환
        val rsList: MutableList<MathResourceDetailVo> = mutableListOf()
        resources.forEach { resourceEntity ->
            val imgList = resourceImg.filter { it.mathResource!!.id == resourceEntity.id }
                .map { MathResourceImgVo(it.imgPath!!, it.imgName!!) }
            val cateList = resourceCate.filter { it.mathResource!!.id == resourceEntity.id }
                .map { MathResourceCateVo(it.mainCateId, it.midCateId) }

            rsList.add(
                MathResourceDetailVo(
                    id = resourceEntity.id,
                    title = resourceEntity.title!!,
                    imgPath = resourceEntity.imgPath!!,
                    imgName = resourceEntity.imgName!!,
                    pptPath = resourceEntity.pptPath!!,
                    pptName = resourceEntity.pptName!!,
                    pptPageCnt = resourceEntity.pptPageCnt,
                    downCnt = resourceEntity.downCnt,
                    imgList = imgList,
                    cateList = cateList,
                    sysCreateDate = resourceEntity.sysCreateDate!!,
                    sysUpdateDate = resourceEntity.sysUpdateDate!!,
                )
            )
        }
        return rsList
    }


    override fun countByMemberId(memberId: UUID): Long =
        queryFactory
            .select(mathResourceEntity.id.count())
            .from(mathResourceEntity)
            .innerJoin(mathResourceEntity.mathResourceCate, mathResourceCateEntity)
            .where(mathResourceCateEntity.mathResource.memberId.eq(memberId))
            .fetchFirst()
}