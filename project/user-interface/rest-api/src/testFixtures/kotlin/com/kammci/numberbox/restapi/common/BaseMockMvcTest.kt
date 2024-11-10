package com.kammci.numberbox.restapi.common

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.reflect.KClass

open class BaseMockMvcTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    val objectMapper: ObjectMapper = ObjectMapper()

    // json GET 요청
    fun getRequest(url: String) =
        getRequest(url, mapOf())

    fun getRequest(url: String, queryMap: Map<String, String>?): ResultActions {
        val queryString = queryMap?.entries?.joinToString("&") { (key, value) -> "${key}=${value}" }
        return mockMvc
            .perform(
                MockMvcRequestBuilders.get("${url}?$queryString")
            )
    }

    // json POST 요청
    fun postRequest(url: String) = postRequest(url, null)

    fun postRequest(url: String, reqBody: Map<String, Any>?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }

    // json PUT 요청
    fun putRequest(url: String) =
        putRequest(url, mapOf())

    fun putRequest(url: String, reqBody: Map<String, Any>?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }

    // json DELETE 요청
    fun delRequest(url: String) =
        delRequest(url, mapOf())

    fun delRequest(url: String, reqBody: Map<String, Any>?): ResultActions {
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

    fun assert2xx(resultActions: ResultActions) {
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    fun assert4xx(resultActions: ResultActions) {
        resultActions.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    // 예외 타입 체크
    fun <T : Throwable> assertException(
        resultActions: ResultActions,
        expectedException: KClass<T>,
    ) {
        resultActions.andExpect { result ->
            val exception = result.resolvedException // 발생한 예외 추출
            assert(expectedException.isInstance(exception))
        }
    }


}