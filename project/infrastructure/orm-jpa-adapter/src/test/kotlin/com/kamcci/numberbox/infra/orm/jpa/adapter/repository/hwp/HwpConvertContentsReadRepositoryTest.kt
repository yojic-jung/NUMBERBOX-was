package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.hwp.HwpConvertContentDummyFactory.getHwpContentsDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class HwpConvertContentsReadRepositoryTest(
    @Autowired
    private val hwpConvertContentsReadRepository: HwpConvertContentsReadRepository
) : BaseRepository() {
    @Test
    fun `변환 컨텐츠 조회 - 성공`() {
        // given
        val dummyEntity = getHwpContentsDummyEntity()
        val memberId = dummyEntity.memberId

        // when
        val hwpList = hwpConvertContentsReadRepository.readAllByMemberId(memberId)

        // then
        assertThat(hwpList[0].id).isEqualTo(dummyEntity.id)
    }
}