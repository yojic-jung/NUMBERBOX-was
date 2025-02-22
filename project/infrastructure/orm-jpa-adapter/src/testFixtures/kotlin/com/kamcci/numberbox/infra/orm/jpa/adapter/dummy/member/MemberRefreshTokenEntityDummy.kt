package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member

import java.util.*

object MemberRefreshTokenEntityDummy {
    // 토큰 만료된 엔티티 조회
    fun getExpiredTokenEntity() = ExistEntityInfo(
        id = 1,
        token = "eyJhbGciOiJIUzI1NiJ9.eyJuc29vaGFrLmNvbSI6dHJ1ZSwiaXNzIjoibnNvb2hhayIsInN1YiI6Im5zb29oYWtSZWZyZXNoVG9rZW4iLCJhdWQiOiJ1c2VyIiwiZXhwIjoxNzAxMTU1NjI3LCJpYXQiOjE2OTg1NjM2Mjd9.IRiJaK2jH-3DskfW4N2Rhm9eVzhB9Mswp8-JlfDN-Ws",
        memberId = UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20")
    )

    // 존재하는 엔티티 정보
    class ExistEntityInfo(val id: Long, val token: String, val memberId: UUID)
}