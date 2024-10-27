package com.kamcci.numberbox.app.domain.exception

import com.kamcci.numberbox.app.domain.exception.code.BaseErrCodeType

data class BusinessValidException(val msg: String) : RuntimeException(msg) {
    constructor(errType: BaseErrCodeType) : this(msg = "[NB-APP-ERR-${errType.code}] : ${errType.message}")
}