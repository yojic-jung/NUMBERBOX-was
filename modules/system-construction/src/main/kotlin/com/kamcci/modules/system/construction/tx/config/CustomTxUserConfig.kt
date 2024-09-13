package com.kamcci.modules.system.construction.tx.config

import com.kamcci.numberbox.app.domain.system_construction.TXExecute

/**
 * Def : 트랜잭션을 사용하게 하는 사용자 정의 어노테이션 설정
 */
object CustomTxUserConfig {
    val CUSTOM_TX_ANNOTATION = TXExecute::class
}