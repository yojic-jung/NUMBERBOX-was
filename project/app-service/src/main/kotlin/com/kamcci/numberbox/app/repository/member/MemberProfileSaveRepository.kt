package com.kamcci.numberbox.app.repository.member

import java.util.*

interface MemberProfileSaveRepository {
    fun save(uuid: UUID, nickName: String): Long
}