package com.kamcci.modules.system.construction.tx.config

import com.kamcci.numberbox.app.domain.system_construction.TxExecute

/**
 * Def : 트랜잭션을 사용하게 하는 사용자 정의 어노테이션 설정
 */
object CustomTxAnnotationConstant {
    val CUSTOM_TX_ANNOTATION = TxExecute::class
}