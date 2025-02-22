package com.kamcci.numberbox.infra.orm.jpa.adapter.annotation

import com.kamcci.numberbox.infra.orm.jpa.adapter.config.QueryDslConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

/**
 * Def.  flyway 적용 DB (로컬 테스트용 db로 접근)
 * Desc. @Transactional 없이도 멱등성 보장함(flyway에 의해)
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfiguration::class])
@ActiveProfiles("orm-jpa-adapter-local-test")
annotation class LocalDBSpringTest