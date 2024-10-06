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

    // json GET 요청
    fun requestGet(url: String) =
        requestGet(url, mapOf())

    fun requestGet(url: String, queryMap: Map<String, String>?): ResultActions {
        val queryString = queryMap?.entries?.joinToString("&") { (key, value) -> "${key}=${value}" }
        return mockMvc
            .perform(
                MockMvcRequestBuilders.get("${url}?$queryString")
            )
    }

    // json POST 요청
    fun requestJsonPost(url: String) = requestJsonPost(url, null)

    fun requestJsonPost(url: String, reqBody: Map<String, Any>?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }

    // json PUT 요청
    fun requestJsonPut(url: String) =
        requestJsonPut(url, mapOf())

    fun requestJsonPut(url: String, reqBody: Map<String, Any>?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }

    // json DELETE 요청
    fun requestJsonDel(url: String) =
        requestJsonDel(url, mapOf())

    fun requestJsonDel(url: String, reqBody: Map<String, Any>?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }


    // response
    fun takeJsonResponse(resultAction: ResultActions) =
        objectMapper.readTree(resultAction.andReturn().response.contentAsString)

    fun takeJsonResponseData(resultAction: ResultActions) =
        objectMapper.readTree(resultAction.andReturn().response.contentAsString).get("data")

    fun removeQuotes(inp: Any) = removeQuotes(inp.toString())

    fun removeQuotes(input: String): String = input.removePrefix("\"").removeSuffix("\"")
}