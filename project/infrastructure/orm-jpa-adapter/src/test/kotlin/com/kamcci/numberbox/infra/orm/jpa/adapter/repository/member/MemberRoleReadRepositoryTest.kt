package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberRoleReadRepositoryTest(
    @Autowired
    private val memberRoleReadRepository: MemberRoleReadRepository
) {
    companion object {
        const val EXIST_ID = "10CA3122-CDA8-EA4D-9BC7-037CB86FDB20"
    }

    @Test
    fun `사용자 권한 조회 - memberId`() {
        // given
        val memberId = UUID.fromString(EXIST_ID)

        // when
        val roleList = memberRoleReadRepository.readRoleByMemberId(memberId)

        // then
        assertThat(roleList.size).isGreaterThan(0)
    }
}