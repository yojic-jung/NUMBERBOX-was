package com.kamcci.modules.system.construction.mock.common

import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.SimpleTransactionStatus

class MockPlatformTransactionManager : PlatformTransactionManager {
    override fun getTransaction(definition: TransactionDefinition?): TransactionStatus {
        return SimpleTransactionStatus()
    }

    override fun commit(status: TransactionStatus) {
    }

    override fun rollback(status: TransactionStatus) {
    }
}