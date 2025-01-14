package com.kamcci.modules.logging.engine.filter

import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.ContentCachingRequestWrapper

@WebMvcTest(controllers = [TestLoggingController::class]) // 컨트롤러 설정
class HttpRequestLoggingFilterTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    fun `should wrap request in ContentCachingRequestWrapper`() {
        // when
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/test")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

    }
}

@RestController
class TestLoggingController {
    @GetMapping("/test")
    fun test(request: HttpServletRequest) {
        // 필터 정상적으로 동작하면 ClassCastException 오류 없이 200 반환
        request as ContentCachingRequestWrapper
    }
}
