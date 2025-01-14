package com.kamcci.modules.logging.engine

import com.kamcci.modules.logging.engine.config.LoggingConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@Import(LoggingConfig::class)
@SpringBootApplication
class LoggingEngineTestApplication