package com.kamcci.numberbox.app.domain.exception

data class BusinessSeverException(val msg: String) : RuntimeException(msg)