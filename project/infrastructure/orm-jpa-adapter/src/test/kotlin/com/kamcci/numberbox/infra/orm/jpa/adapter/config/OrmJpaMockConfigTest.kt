//package com.kamcci.numberbox.infra.orm.jpa.adapter.config
//
//import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBSpringMockConfigTest
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//
//@TcDBSpringMockConfigTest
//class OrmJpaMockConfigTest @Autowired constructor(
//    private val ormJpaMockConfig: OrmJpaMockConfig,
//) {
//    @Test
//    fun `mockConfig 정상 설정 확인`() {
//        assertThat(ormJpaMockConfig.sysGarbageFileWriteRepository).isNotNull
//        assertThat(ormJpaMockConfig.memberRefreshTokenRepo).isNotNull
//    }
//}