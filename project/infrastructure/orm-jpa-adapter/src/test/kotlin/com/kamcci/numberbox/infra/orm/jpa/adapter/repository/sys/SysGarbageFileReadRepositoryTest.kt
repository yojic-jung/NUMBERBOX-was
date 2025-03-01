package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class SysGarbageFileReadRepositoryTest @Autowired constructor(
    private val sysGarbageFileReadRepository: SysGarbageFileReadRepository
) {
    @Test
    fun `삭제 대상 파일 조회`() {
        // given
        val limit = 1L

        // when
        val garbageFile = sysGarbageFileReadRepository.readAllByType(GarbageFileType.S3, limit)

        // then
        assertThat(garbageFile.size).isEqualTo(limit)
    }

}