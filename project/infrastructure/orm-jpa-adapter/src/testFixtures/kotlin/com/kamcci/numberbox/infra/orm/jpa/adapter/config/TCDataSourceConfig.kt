package com.kamcci.numberbox.infra.orm.jpa.adapter.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

/**
 * TestContainer가 생성한 DB로 connection연결
 */
@Profile("orm-jpa-adapter-tc-test")
@TestConfiguration
class TCDataSourceConfig {
    @Bean
    fun dataSource(): HikariDataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .url(MysqlTCExtension.mysqlContainer.jdbcUrl)
            .username(MysqlTCExtension.mysqlContainer.username)
            .password(MysqlTCExtension.mysqlContainer.password)
            .build()
    }

}