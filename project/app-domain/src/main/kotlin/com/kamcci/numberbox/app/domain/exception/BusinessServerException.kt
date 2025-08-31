package com.kamcci.numberbox.app.domain.exception

data class BusinessServerException(val msg: String) : RuntimeException(msg)