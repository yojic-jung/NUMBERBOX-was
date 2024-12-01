package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class SysGarbageFileWriteRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val sysGarbageFileWriteRepository: SysGarbageFileWriteRepository
) {
    @Test
    fun `삭제 대상 파일 저장`() {
        // given
        val fileDeleteDto = FileDeleteDto(GarbageFileType.S3, "", "")

        // when
        val id = sysGarbageFileWriteRepository.create(fileDeleteDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `삭제`() {
        // given
        val idList = listOf(1L)

        // when
        val executeRowCnt = sysGarbageFileWriteRepository.deleteById(idList)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }

    @Test
    fun `파일 삭제 실패 카운트 변경`() {
        // given
        val idList = listOf(1L)

        // when
        val executeRowCnt = sysGarbageFileWriteRepository.incrementFailCntById(idList)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }
}