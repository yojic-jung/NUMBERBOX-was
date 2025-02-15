package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class HwpConvertContentsWriteRepositoryTest(
    @Autowired
    private val hwpConvertContentsWriteRepository: HwpConvertContentsWriteRepository
) : BaseRepository() {
    @Test
    fun `변환 컨텐츠 저장 - 성공`() {
        // given
        val createDto = HwpConvertContentsCreateDto(
            memberId = UUID.randomUUID(),
            isConverted = true,
            fileName = "",
            contents = "",
            imgPath = ""
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
        val udpateDto = HwpConvertContentsUpdateDto(
            id = 1L,
            memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20"),
            contents = "",
            isGrammarConverted = true
        )

        // when
        val executeRowCnt = hwpConvertContentsWriteRepository.update(udpateDto)

        // then
        assertThat(executeRowCnt).isOne()
    }

    @Test
    fun `변환 컨텐츠 삭제 - 성공`() {
        // given
        val id = 1L
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val executeRowCnt = hwpConvertContentsWriteRepository.delete(id, memberId)

        // then
        assertThat(executeRowCnt).isOne()
    }
}