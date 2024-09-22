package com.kamcci.numberbox.app.port.repository.member

import java.util.*

interface MemberSaveOrmPort {
    fun save(email: String, password: String): UUID
}