package com.kamcci.numberbox.app.port.repository.member

import java.util.*

interface MemberProfileSaveOrmPort {
    fun save(uuid: UUID, nickName: String): Long
}