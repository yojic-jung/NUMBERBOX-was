package com.kamcci.numberbox.infra.orm.annotation

import com.kamcci.numberbox.infra.orm.config.QueryDslConfig
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

/**
 * Def.  flyway 적용 DB (테스트 컨테이너로 제공)
 * Desc. 멱등성을 보장함
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfig::class])
@ActiveProfiles("orm-adapter-local-test")
annotation class LocalDBJpaTest