package com.kamcci.numberbox.restapi.config.stub

import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.app.service.stub.port.hwp.MockHwpSocketClient
import com.kamcci.numberbox.app.service.stub.usecase.hwp.MockHwpConvertContentsReadCase
import com.kamcci.numberbox.app.service.stub.usecase.hwp.MockHwpConvertContentsWriteCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import com.kamcci.numberbox.restapi.stub.hwp.MockHwpConvertFileUtil
import com.kamcci.numberbox.restapi.util.hwp.HwpConvertFileUtil
import org.springframework.context.annotation.Bean

class HwpMockBeanConfig {
    @Bean
    fun hwpSocketClient(): HwpSocketClient = MockHwpSocketClient()

    @Bean
    fun hwpConvertContentsWriteCase(): HwpConvertContentsWriteCase = MockHwpConvertContentsWriteCase()

    @Bean
    fun hwpConvertContentsReadCase(): HwpConvertContentsReadCase = MockHwpConvertContentsReadCase()

    @Bean
    fun hwpConvertFileUtil(): HwpConvertFileUtil = MockHwpConvertFileUtil()
}