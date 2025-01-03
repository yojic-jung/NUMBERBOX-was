package com.kamcci.numberbox.app.domain.exception

import com.kamcci.numberbox.app.domain.exception.code.BaseErrCodeType

data class BusinessInValidException(val msg: String) : RuntimeException(msg) {
    constructor(errType: BaseErrCodeType) : this(msg = "[NB-APP-ERR-${errType.code}] : ${errType.message}")
    constructor(
        errType: BaseErrCodeType,
        throwable: Throwable
    ) : this(msg = "[NB-APP-ERR-${errType.code}] : ${errType.message}") {
        initCause(throwable)
    }
}