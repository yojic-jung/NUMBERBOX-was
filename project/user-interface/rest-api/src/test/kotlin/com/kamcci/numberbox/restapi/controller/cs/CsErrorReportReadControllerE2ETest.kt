//package com.kamcci.numberbox.restapi.controller.cs
//
//import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
//import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBConfig
//import com.kamcci.numberbox.restapi.RestApiApplication
//import com.kamcci.numberbox.restapi.annotation.SpringE2EConfig
//import org.junit.jupiter.api.Disabled
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.context.annotation.FilterType
//import org.springframework.scheduling.annotation.EnableScheduling
//import java.util.*
//
//@Disabled
//@TcDBConfig
//@SpringE2EConfig
//@ComponentScan(
//    basePackages = ["com.kamcci.numberbox", "com.kamcci.modules.system.construction"],
//    excludeFilters = [ComponentScan.Filter(
//        type = FilterType.ASPECTJ,
//        pattern = ["com.kamcci.numberbox.restapi.config..*"]
//    )]
//)
//@EnableScheduling
//@SpringBootTest(classes = [RestApiApplication::class])
//class CsErrorReportReadControllerE2ETest(
//    @Autowired
//    private val csErrorReportReadCase: CsErrorReportReadCase
//) {
//    companion object {
//        // 고객센터 내 문의 내역
//        const val MY_CS_ERROR = "/cs/error/my"
//    }
//
//    @Test
//    fun `고객센터 내 문의 내역 - 성공`() {
//        // given
//        csErrorReportReadCase.readByMemberId(UUID.randomUUID())
//        //when
//
//        // then
//    }
//}