//package com.kamcci.numberbox
//
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito
//import org.springframework.boot.SpringApplication
//
//class BootstrapApplicationKtTest {
//    @Test
//    fun testRunApplicationWithArgs() {
//        // Given: SpringApplication.run을 Mock 처리
//        val mockRunMethod = Mockito.mockStatic(SpringApplication::class.java)
//
//        // When: main 함수 호출
//        main(emptyArray())
//
//        // Then: SpringApplication.run 호출 여부 확인
//        mockRunMethod.verify { SpringApplication.run(BootstrapApplication::class.java, *emptyArray()) }
//
//        // Clean up Mock
//        mockRunMethod.close()
//
//    }
//}