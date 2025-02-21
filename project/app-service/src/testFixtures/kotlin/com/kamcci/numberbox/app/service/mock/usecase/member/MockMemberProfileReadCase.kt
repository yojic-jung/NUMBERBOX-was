package com.kamcci.numberbox.app.service.mock.usecase.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.sample.MemberSampleData.getMemberProfileVo
import com.kamcci.numberbox.app.service.sample.MemberSampleData.getMemberProfileVoList
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import java.util.*

class MockMemberProfileReadCase : MemberProfileReadCase {
    companion object {
        // 분기 테스트를 위한 파일명에 null or empty를 갖는 id
        val FILE_NULL_OR_EMPTY_ID1 = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID2 = UUID.fromString("10ad5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID3 = UUID.fromString("10bd5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID4 = UUID.fromString("10cd5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID5 = UUID.fromString("10dd5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID6 = UUID.fromString("10fd5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID7 = UUID.fromString("10fa5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID8 = UUID.fromString("10fb5466-cda8-ea4d-9bc7-037cb86fdb20")
        val FILE_NULL_OR_EMPTY_ID9 = UUID.fromString("10fc5466-cda8-ea4d-9bc7-037cb86fdb20")

    }

    override fun readByMemberId(memberId: UUID): MemberProfileVo {
        return getMemberProfileVo()
    }

    override fun readByProfileId(profileId: Long): MemberProfileVo? {
        return if (profileId == FAIL_ID) null else getMemberProfileVo()
    }

    override fun readByProfileIdList(profileId: List<Long>): List<MemberProfileVo> {
        return getMemberProfileVoList(100)
    }

    override fun readProfileImgByMemberId(memberId: UUID): MemberProfileImgVo? {
        return when (memberId) {
            FILE_NULL_OR_EMPTY_ID1 -> MemberProfileImgVo(1L, UUID.randomUUID(), null, null)
            FILE_NULL_OR_EMPTY_ID2 -> MemberProfileImgVo(1L, UUID.randomUUID(), "", "")
            FILE_NULL_OR_EMPTY_ID3 -> MemberProfileImgVo(1L, UUID.randomUUID(), null, "")
            FILE_NULL_OR_EMPTY_ID4 -> MemberProfileImgVo(1L, UUID.randomUUID(), "", null)
            FILE_NULL_OR_EMPTY_ID5 -> MemberProfileImgVo(1L, UUID.randomUUID(), "123", null)
            FILE_NULL_OR_EMPTY_ID6 -> MemberProfileImgVo(1L, UUID.randomUUID(), null, "213")
            FILE_NULL_OR_EMPTY_ID7 -> MemberProfileImgVo(1L, UUID.randomUUID(), "", "213")
            FILE_NULL_OR_EMPTY_ID8 -> MemberProfileImgVo(1L, UUID.randomUUID(), "213", "")
            FILE_NULL_OR_EMPTY_ID9 -> null
            else -> MemberProfileImgVo(1L, UUID.randomUUID(), "213", "13")
        }
    }

    override fun readProfileIdByMemberId(memberId: UUID): Long? {
        return if (memberId == FAIL_MEMBER_ID) null else 1L
    }
}