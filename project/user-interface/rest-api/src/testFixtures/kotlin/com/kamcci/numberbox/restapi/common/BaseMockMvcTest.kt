package com.kamcci.numberbox.restapi.common

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.reflect.KClass

open class BaseMockMvcTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    val objectMapper: ObjectMapper = ObjectMapper()

    /**
     * Rest API 요청
     */

    // json GET 요청
    fun getRequest(url: String) =
        getRequest(url, mapOf())

    fun getRequest(url: String, queryMap: Map<String, String?>?): ResultActions {
        val queryString = queryMap?.entries?.joinToString("&") { (key, value) -> "${key}=${value}" }
        return mockMvc
            .perform(
                MockMvcRequestBuilders.get("${url}?$queryString")
            )
    }

    // json POST 요청
    fun postRequest(url: String, reqBody: Any?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }

    // multipartform POST 요청
    fun postMultipartForm(url: String, reqBody: Map<String, String>, fileList: List<MockMultipartFile>): ResultActions {
        var requestBuilder = MockMvcRequestBuilders.multipart(url)
        for (file in fileList) {
            requestBuilder = requestBuilder.file(file)
        }

        for ((key, value) in reqBody) {
            requestBuilder.param(key, value)
        }
        requestBuilder.contentType(MediaType.MULTIPART_FORM_DATA)

        return mockMvc.perform(requestBuilder)
    }

    fun postMultipartForm(url: String, multipartFile: MockMultipartFile): ResultActions {
        var requestBuilder = MockMvcRequestBuilders.multipart(url)
        requestBuilder = requestBuilder.file(multipartFile)
        requestBuilder.contentType(MediaType.MULTIPART_FORM_DATA)

        return mockMvc.perform(requestBuilder)
    }

    // json PUT 요청
    fun putRequest(url: String, reqBody: Any?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }

    // multipartform PUT 요청
    fun putMultipartForm(url: String, fileList: List<MockMultipartFile>): ResultActions {
        return putMultipartForm(url, null, fileList)
    }

    fun putMultipartForm(
        url: String,
        reqBody: Map<String, String>?,
        fileList: List<MockMultipartFile>
    ): ResultActions {
        val requestBuilder = MockMvcRequestBuilders.multipart(url)
        for (file in fileList) {
            requestBuilder.file(file)
        }
        requestBuilder.contentType(MediaType.MULTIPART_FORM_DATA)
        requestBuilder.with { request ->
            request.method = "PUT"
            request
        }

        if (reqBody != null) {
            for ((key, value) in reqBody) {
                requestBuilder.param(key, value)
            }
        }

        return mockMvc.perform(requestBuilder)
    }


    // json DELETE 요청
    fun delRequest(url: String) = delRequest(url, null)
    fun delRequest(url: String, reqBody: Map<String, Any>?): ResultActions {
        val reqBuilder = MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
        if (reqBody != null) {
            reqBuilder.content(objectMapper.writeValueAsString(reqBody))
        }
        return mockMvc.perform(reqBuilder)
    }

    /**
     * 결과 검증
     */
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

    // 예외 메시지 체크
    fun assertExMsg(
        resultActions: ResultActions,
        exMsg: String
    ) {
        resultActions.andExpect { result ->
            val exception = result.resolvedException // 발생한 최상위 예외
            assertThat(exception).isNotNull

            // 예외 추적하며 래핑한 예외 메시지 전부 추출
            val messages = mutableListOf<String>()
            var currentException: Throwable? = exception as Throwable
            while (currentException != null) {
                messages.add(currentException.message ?: "")
                currentException = currentException.cause
            }

            // 래핑하며 변경된 예외메시지들 중 하나라도 일치하는게 있는지 체크
            val matchFound = messages.any { it.contains(exMsg) }
            assertThat(matchFound).isTrue()
        }
    }


    /**
     * 응답 값 검증
     */
    fun takeJsonResponseData(resultAction: ResultActions) =
        objectMapper.readTree(resultAction.andReturn().response.contentAsString).get("data")

    fun removeQuotes(inp: Any) = removeQuotes(inp.toString())

    fun removeQuotes(input: String): String = input.removePrefix("\"").removeSuffix("\"")

}