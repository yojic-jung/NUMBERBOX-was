package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathResourceWriteRepositoryTest(
    @Autowired
    private val mathResourceWriteRepository: MathResourceWriteRepository
) {
    @Test
    fun `학습자료 생성`() {
        // given
        val createDto =
            MathResourceCreateDto(UUID.randomUUID(), "", "", "", 5, "", "", listOf("1-1"), listOf(FileNameVo("", "")))

        // when
        val id = mathResourceWriteRepository.create(createDto)

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `학습자료 수정`() {
        // given
        val updateDto =
            MathResourceUpdateDto(1L, "", "", "", 5, "", "", listOf("1-1"), listOf(FileNameVo("", "")))

        // when
        assertDoesNotThrow {
            mathResourceWriteRepository.update(updateDto)
        }
    }

    @Test
    fun `학습자료 삭제`() {
        // given
        val id = 1L
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val executeRowCnt = mathResourceWriteRepository.deleteByIdAndMemberId(id, memberId)

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }
}