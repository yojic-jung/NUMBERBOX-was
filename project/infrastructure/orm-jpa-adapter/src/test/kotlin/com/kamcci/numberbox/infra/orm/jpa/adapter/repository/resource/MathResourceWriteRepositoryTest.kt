package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource.MathResourceDummyFactory.getMathResourceDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathResourceWriteRepositoryTest @Autowired constructor(
    private val mathResourceWriteRepository: MathResourceWriteRepository
) {
    private val mathResourceDummy = getMathResourceDummyEntity()

    @Test
    fun `학습자료 생성`() {
        // given
        val createDto =
            MathResourceCreateDto(
                UUID.randomUUID(),
                "any",
                "",
                "",
                5,
                "",
                "",
                listOf("1-1"),
                listOf(FileNameVo("", ""))
            )

        // when
        val id = mathResourceWriteRepository.create(createDto)

        // then
        assertThat(id).isPositive()
    }

    @Test
    fun `학습자료 수정`() {
        // given
        val resourceId = mathResourceDummy.id
        val updateDtoList =
            listOf(
                MathResourceUpdateDto(resourceId, "any", "", "", 5, "", "", listOf("1-1"), listOf(FileNameVo("", ""))),
                MathResourceUpdateDto(resourceId, "any", "", "", 5, "", "", listOf("1-1"), listOf()),
            )

        // when
        for (updateDto in updateDtoList) {
            assertDoesNotThrow {
                mathResourceWriteRepository.update(updateDto)
            }
        }
    }

    @Test
    fun `학습자료 삭제`() {
        // when
        val executeRowCnt =
            mathResourceWriteRepository.deleteByIdAndMemberId(mathResourceDummy.id, mathResourceDummy.memberId)

        // then
        assertThat(executeRowCnt).isPositive()
    }
}