package com.kamcci.numberbox.infra.orm.jpa.adapter.annotation

import com.kamcci.numberbox.infra.orm.jpa.adapter.config.MysqlTCExtension
import com.kamcci.numberbox.infra.orm.jpa.adapter.config.QueryDslConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Def.  외부 모듈에서 사용하기 위한 config
 */
@ExtendWith(value = [MysqlTCExtension::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfiguration::class])
// @Entity 어노테이션의 경우 @Component를 포함하지 않기에 별도로 스캔 해줘야함(외부 모듈은 부트스트랩 패키지 경로가 다르기에 설정 필요)
@EntityScan(basePackages = ["com.kamcci.numberbox.infra.orm.jpa.adapter.entity"])
// JpaRepository의 경우 @Component를 포함하지 않기에 별도로 스캔 해줘야함
@EnableJpaRepositories(basePackages = ["com.kamcci.numberbox.infra.orm.jpa.adapter.repository"])
annotation class TcDBConfig