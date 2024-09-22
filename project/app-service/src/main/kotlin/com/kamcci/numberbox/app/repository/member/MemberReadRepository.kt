package com.kamcci.numberbox.app.repository.member

import java.time.LocalDateTime
import java.util.*

interface MemberReadRepository {

    fun findIdByEmail(email: String): UUID?

    fun findFailCountById(id: UUID): Int?

    fun findLastFailTimeById(id: UUID): LocalDateTime?

    fun existsByEmail(email: String): Boolean
}