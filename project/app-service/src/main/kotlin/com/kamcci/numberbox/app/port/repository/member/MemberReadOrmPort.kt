package com.kamcci.numberbox.app.port.repository.member

import java.time.LocalDateTime
import java.util.*

interface MemberReadOrmPort {

    fun findIdByEmail(email: String): UUID?

    fun findFailCountById(id: UUID): Int?

    fun findLastFailTimeById(id: UUID): LocalDateTime?

    fun existsByEmail(email: String): Boolean
}