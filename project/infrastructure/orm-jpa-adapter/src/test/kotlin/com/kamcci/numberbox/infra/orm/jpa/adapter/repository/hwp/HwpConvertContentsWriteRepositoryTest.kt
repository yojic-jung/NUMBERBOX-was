package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.hwp.HwpConvertContentDummyFactory.getHwpContentsDummyEntity4Del
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.hwp.HwpConvertContentDummyFactory.getHwpContentsDummyEntity4Updt
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class HwpConvertContentsWriteRepositoryTest @Autowired constructor(
    private val hwpConvertContentsWriteRepository: HwpConvertContentsWriteRepository
) : BaseRepository() {
    @Test
    fun `변환 컨텐츠 저장 - 성공`() {
        // given
        val createDto = HwpConvertContentsCreateDto(
            memberId = getMemberDummyEntity().memberId,
            isConverted = true,
            fileName = "any",
            contents = "any",
            imgPath = "any"
        )

        // when
        val id = hwpConvertContentsWriteRepository.create(createDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `변환 컨텐츠 수정 - 성공`() {
        // given
        val existEntity = getHwpContentsDummyEntity4Updt()
        val updateDto = HwpConvertContentsUpdateDto(
            id = existEntity.id,
            memberId = existEntity.memberId,
            contents = "any",
            isGrammarConverted = true
        )

        // when
        val executeRowCnt = hwpConvertContentsWriteRepository.update(updateDto)

        // then
        assertThat(executeRowCnt).isOne()
    }

    @Test
    fun `변환 컨텐츠 삭제 - 성공`() {
        // given
        val existEntity = getHwpContentsDummyEntity4Del()

        // when
        val executeRowCnt = hwpConvertContentsWriteRepository.delete(existEntity.id, existEntity.memberId)

        // then
        assertThat(executeRowCnt).isOne()
    }
}