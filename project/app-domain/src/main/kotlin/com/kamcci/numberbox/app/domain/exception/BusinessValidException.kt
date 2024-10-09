package com.kamcci.numberbox.app.domain.exception

data class BusinessValidException(val msg: String, val showMsg: Boolean = true) : RuntimeException(msg)