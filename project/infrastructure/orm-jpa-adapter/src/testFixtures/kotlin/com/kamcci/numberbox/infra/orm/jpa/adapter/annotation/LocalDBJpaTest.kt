package com.kamcci.numberbox.infra.orm.jpa.adapter.annotation

import com.kamcci.numberbox.infra.orm.jpa.adapter.config.MockOrmBeanConfig
import com.kamcci.numberbox.infra.orm.jpa.adapter.config.QueryDslConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

/**
 * Def.  flyway 적용 DB (로컬 테스트용 db로 접근)
 * Desc. 멱등성을 보장함
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfiguration::class, MockOrmBeanConfig::class])
@ActiveProfiles("orm-jpa-adapter-local-test")
annotation class LocalDBJpaTest