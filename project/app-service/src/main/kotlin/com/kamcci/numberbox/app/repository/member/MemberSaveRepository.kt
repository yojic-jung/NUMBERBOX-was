package com.kamcci.numberbox.app.repository.member

import java.util.*

interface MemberSaveRepository {
    fun save(email: String, password: String): UUID
}