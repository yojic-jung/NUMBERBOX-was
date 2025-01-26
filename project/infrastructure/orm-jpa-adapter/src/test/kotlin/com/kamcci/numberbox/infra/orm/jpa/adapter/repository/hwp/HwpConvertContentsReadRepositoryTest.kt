package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class HwpConvertContentsReadRepositoryTest(
    @Autowired
    private val hwpConvertContentsReadRepository: HwpConvertContentsReadRepository
) : BaseRepository() {
    @Test
    fun `변환 컨텐츠 조회 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val hwpList = hwpConvertContentsReadRepository.readAllByMemberId(memberId)

        // then
        assertThat(hwpList.size).isEqualTo(1)
    }
}