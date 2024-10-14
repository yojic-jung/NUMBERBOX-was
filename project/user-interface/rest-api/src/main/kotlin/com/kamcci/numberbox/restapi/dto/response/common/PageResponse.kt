package com.kamcci.numberbox.restapi.dto.response.common

import com.kamcci.numberbox.app.domain.dto.common.PageRequest

interface PageResponse<T> {
    val contents: List<T>
    val page: PageRequest
    val total: Long
}
