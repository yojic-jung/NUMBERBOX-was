package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberRoleModifyOrmAdapterTest(
    @Autowired
    private val memberRoleModifyRepo: MemberRoleModifyOrmAdapter
) {
    companion object {
        const val MEMBER_ID = "10ca3122-cda8-ea4d-9bc7-037cb86fdb20"
        const val NONE_EXIST_MEMBER_ID = "12ca3122-cda8-ea4d-9bc7-037cb86fdb20"
    }

    @Test
    fun `권한 enabled 수정 - 성공`() {
        // given
        val memberId = UUID.fromString(MEMBER_ID)
        val enabled = true

        // when
        val isUpdated = memberRoleModifyRepo.updateEnabledById(memberId, enabled)

        // then
        assertThat(isUpdated).isTrue()
    }

    @Test
    fun `존재하지 않는 계정 권한 enabled 수정 - 실패`() {
        // given
        val memberId = UUID.fromString(NONE_EXIST_MEMBER_ID)
        val enabled = true

        // when
        val isUpdated = memberRoleModifyRepo.updateEnabledById(memberId, enabled)

        // then
        assertThat(isUpdated).isFalse()
    }
}