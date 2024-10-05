package com.kammci.numberbox.restapi.common

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

open class BaseMockMvcTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    val objectMapper: ObjectMapper = ObjectMapper()

    // json POST 요청
    fun requestJsonPost(url: String, reqBody: Map<String, Any>) =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reqBody))
            )

    // json POST 요청
    fun requestGET(url: String, queryMap: Map<String, String>): ResultActions {
        val queryString = queryMap.entries.joinToString("&") { (key, value) -> "${key}=${value}" }
        return mockMvc
            .perform(
                MockMvcRequestBuilders.get("${url}?$queryString")
            )
    }
}