package com.kamcci.numberbox.infra.orm.annotation

import com.kamcci.numberbox.infra.orm.config.MysqlTCExtension
import com.kamcci.numberbox.infra.orm.jpa.adapter.config.QueryDslConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

/**
 * Def.  flyway 적용 DB (테스트 컨테이너로 제공)
 * Desc. @Transactional 없이도 멱등성 보장함(flyway에 의해)
 */
@SpringBootTest
@ExtendWith(value = [MysqlTCExtension::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfig::class])
@ActiveProfiles("orm-jpa-adapter-tc-test")
annotation class TcDBSpringTest