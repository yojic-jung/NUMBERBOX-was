package com.kamcci.numberbox.infra.orm.jpa.adapter.sample.common

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import java.util.*

object CommonSampleData {

    fun getHttpResponseLoggingDto(httpStatus: Int) = HttpResponseLoggingDto(httpStatus)
    fun getHttpRequestLoggingDto(memberId: UUID) =
        HttpRequestLoggingDto(memberId, "Chrome", "Mac", "127.0.0.1", "GET", "/sdfa/adf", "sadf")

    fun getClientLoggingInfoEventDto(memberId: UUID, httpStatus: Int = 200) =
        ClientLoggingInfoEventDto(getHttpRequestLoggingDto(memberId), getHttpResponseLoggingDto(httpStatus))
}