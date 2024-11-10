package com.kamcci.numberbox.app.domain.dto.common

interface PageRequest {
    val pageNum: Long
    val pageVolume: Long

    fun getOffset() = pageNum * pageVolume
}