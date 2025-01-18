package com.kamcci.numberbox.app.domain.exception

import com.kamcci.numberbox.app.domain.exception.code.BaseErrCodeType

data class BusinessErrCodeException(val errType: BaseErrCodeType) : RuntimeException(errType.message)