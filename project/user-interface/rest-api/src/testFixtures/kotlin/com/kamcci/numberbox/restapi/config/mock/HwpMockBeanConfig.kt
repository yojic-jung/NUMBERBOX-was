package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.port.hwp.HwpClientPort
import com.kamcci.numberbox.app.service.mock.port.hwp.MockHwpClientAdapter
import com.kamcci.numberbox.app.service.mock.usecase.hwp.MockHwpConvertContentsReadCase
import com.kamcci.numberbox.app.service.mock.usecase.hwp.MockHwpConvertContentsWriteCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import com.kamcci.numberbox.restapi.mock.hwp.MockHwpConvertFileUtil
import com.kamcci.numberbox.restapi.util.hwp.HwpConvertFileUtil
import org.springframework.context.annotation.Bean

class HwpMockBeanConfig {
    @Bean
    fun hwpSocketClient(): HwpClientPort = MockHwpClientAdapter()

    @Bean
    fun hwpConvertContentsWriteCase(): HwpConvertContentsWriteCase = MockHwpConvertContentsWriteCase()

    @Bean
    fun hwpConvertContentsReadCase(): HwpConvertContentsReadCase = MockHwpConvertContentsReadCase()

    @Bean
    fun hwpConvertFileUtil(): HwpConvertFileUtil = MockHwpConvertFileUtil()
}