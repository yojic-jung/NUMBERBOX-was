package com.kamcci.numberbox.app.repository.member

import java.time.LocalDateTime

interface MemberReadRepository {

    fun findIdByEmail(email: String): Long

    fun findFailCountById(userId: Long): Int

    fun findLastFailTimeById(userId: Long): LocalDateTime

}