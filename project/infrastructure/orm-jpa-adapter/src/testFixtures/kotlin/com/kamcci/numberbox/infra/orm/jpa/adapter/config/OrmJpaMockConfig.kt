package com.kamcci.numberbox.infra.orm.jpa.adapter.config

import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenJpaRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.sys.SysGarbageFileWriteRepository
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.SpyBean

@TestConfiguration
class OrmJpaMockConfig {
    @SpyBean
    lateinit var sysGarbageFileWriteRepository: SysGarbageFileWriteRepository

    @SpyBean
    lateinit var memberRefreshTokenRepo: MemberRefreshTokenJpaRepository
}