package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource.MathResourceDummyFactory.NOT_EXIST_RESOURCE_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource.MathResourceDummyFactory.getMathResourceDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource.MathResourceDummyFactory.getMathResourceDummyEntityWithImg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceReadRepositoryTest @Autowired constructor(
    private val mathResourceReadRepository: MathResourceReadRepository
) {
    private val mathResourceDummyWithImg = getMathResourceDummyEntityWithImg()
    private val mathResourceDummy = getMathResourceDummyEntity()

    @Test
    fun `대분류 id로 조회`() {
        // given
        val mainCateId = 1
        val pageRequestImpl = PageRequestImpl(pageNum = 0, pageVolume = 10)

        // when
        val resourceList = mathResourceReadRepository.readByMainCateId(mainCateId, pageRequestImpl)

        // then
        assertThat(resourceList.size).isPositive()
    }

    @Test
    fun `대분류 id로 전체 카운트 조회`() {
        // given
        val mainCateId = 1

        // when
        val cnt = mathResourceReadRepository.countByMainCateId(mainCateId)

        // then
        assertThat(cnt).isPositive()
    }

    @Test
    fun `id로 조회 - 이미지, 카테고리 존재`() {
        // given
        val id = mathResourceDummyWithImg.id

        // when
        val resource = mathResourceReadRepository.readById(id)

        // then
        assertThat(resource.id).isEqualTo(id)
    }

    @Test
    fun `id로 조회 - 이미지, 카테고리 미존재`() {
        // given
        val id = mathResourceDummy.id

        // when
        val resource = mathResourceReadRepository.readById(id)

        // then
        assertThat(resource.id).isEqualTo(id)
    }

    @Test
    fun `memberId로 조회`() {
        // given
        val pageRequestImpl = PageRequestImpl(pageNum = 0, pageVolume = 10)

        // when
        val resourceList = mathResourceReadRepository.readByMemberId(mathResourceDummy.memberId, pageRequestImpl)

        // then
        assertThat(resourceList.size).isPositive()
    }

    @Test
    fun `memberId로 전체 카운트 조회`() {
        // when
        val cnt = mathResourceReadRepository.countByMemberId(mathResourceDummy.memberId)

        // then
        assertThat(cnt).isPositive()
    }

    @Test
    fun `id로 학습 자료 파일 조회 - 미존재`() {
        // given
        val id = NOT_EXIST_RESOURCE_ID

        // when
        assertThrows<IllegalArgumentException> {
            mathResourceReadRepository.readFileById(id)
        }
    }

    @Test
    fun `id로 학습 자료 파일 조회 - 이미지 존재`() {
        // given
        val id = mathResourceDummyWithImg.id

        // when
        val resource = mathResourceReadRepository.readFileById(id)

        // then
        assertThat(resource.imgList).isNotEmpty
    }

    @Test
    fun `id로 학습 자료 파일 조회 - 이미지 미존재`() {
        // given
        val id = mathResourceDummy.id

        // when
        val resource = mathResourceReadRepository.readFileById(id)

        // then
        assertThat(resource.id).isEqualTo(id)
    }
}