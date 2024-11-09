package com.kamcci.numberbox.infra.orm.config

import org.junit.jupiter.api.extension.Extension
import org.testcontainers.containers.MySQLContainer

class MysqlTCExtension : Extension {
    companion object {
        var mysqlContainer: MySQLContainer<Nothing> =
            MySQLContainer<Nothing>("mysql:8.3.0")
                .apply {
                    this.withDatabaseName("numberbox_tc")
                    this.withUsername("dywlr")
                    this.withPassword("1111")
                    this.withUrlParam("characterEncoding", "UTF-8")
                }

        init {
            mysqlContainer.start()
        }
    }
}
