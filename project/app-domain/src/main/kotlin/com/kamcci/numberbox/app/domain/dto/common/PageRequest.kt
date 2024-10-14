package com.kamcci.numberbox.app.domain.dto.common

interface PageRequest {
    val num: Long
    val volume: Long

    fun getOffset() = num * volume

}