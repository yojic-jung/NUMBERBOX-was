package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberProfileReadOrmAdapterTest(
    @Autowired
    private val memberProfileReadOrmAdapter: MemberProfileReadOrmAdapter
) {
    @Test
    fun `멤버 id로 프로필 조회`() {
        // given
        val memberId = UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20")

        // when
        val profile = memberProfileReadOrmAdapter.readByMemberId(memberId)

        // then
        assertThat(profile?.id).isEqualTo(1L)
    }

    @Test
    fun `프로필 id로 프로필 조회`() {
        // given
        val memberId = UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20")

        // when
        val profile = memberProfileReadOrmAdapter.readByMemberId(memberId)

        // then
        assertThat(profile?.id).isEqualTo(1L)
    }

    @Test
    fun `멤버 id로 프로필 id 조회`() {
        // given
        val memberId = UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20")

        // when
        val profileId = memberProfileReadOrmAdapter.readProfileIdByMemberId(memberId)

        // then
        assertThat(profileId).isEqualTo(1L)
    }

    @Test
    fun `멤버 id로 프로필 이미지 조회`() {
        // given
        val memberId = UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20")
        val path = "/test/path"
        val name = "testImg.png"

        // when
        val profileImg = memberProfileReadOrmAdapter.readProfileImgByMemberId(memberId)

        // then
        assertThat(profileImg?.profileImgPath).isEqualTo(path)
        assertThat(profileImg?.profileImgName).isEqualTo(name)
    }

    @Test
    fun `in절로 프로필 조회`() {
        // given
        val profileIds = listOf(1L, 2L)

        // when
        val profiles = memberProfileReadOrmAdapter.readByProfileIdList(profileIds)

        // then
        assertThat(profiles.size).isEqualTo(2)
    }
}