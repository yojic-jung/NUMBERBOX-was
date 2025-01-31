package com.kamcci.numberbox.restapi.stub

import com.kamcci.numberbox.app.service.stub.usecase.docs.MockMathDocsPaperReadCase
import com.kamcci.numberbox.app.service.stub.usecase.docs.MockMathDocsPaperWriteCase
import com.kamcci.numberbox.app.service.stub.usecase.docs.MockMathDocsReadCase
import com.kamcci.numberbox.app.service.stub.usecase.docs.MockMathDocsUsageWriteCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteCase
import org.springframework.context.annotation.Bean

class MathDocsMockBeanConfig {
    @Bean
    fun mathDocsPaperReadCase(): MathDocsPaperReadCase = MockMathDocsPaperReadCase()

    @Bean
    fun mathDocsPaperWriteCase(): MathDocsPaperWriteCase = MockMathDocsPaperWriteCase()

    @Bean
    fun mathDocsReadCase(): MathDocsReadCase = MockMathDocsReadCase()

    @Bean
    fun mathDocsUsageWriteCase(): MathDocsUsageWriteCase = MockMathDocsUsageWriteCase()
}