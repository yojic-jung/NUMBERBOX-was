package com.kamcci.numberbox.app.domain.exception

data class BusinessInValidException(val msg: String) : RuntimeException(msg)