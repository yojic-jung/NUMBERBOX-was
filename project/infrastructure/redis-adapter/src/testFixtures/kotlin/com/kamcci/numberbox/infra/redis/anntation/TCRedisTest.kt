package com.kamcci.numberbox.infra.redis.anntation

import com.kamcci.numberbox.infra.redis.config.TestContainerConfig
import com.kamcci.numberbox.infra.redis.config.TestRedisConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestRedisConfig::class)
@ExtendWith(TestContainerConfig::class)
@SpringBootTest
annotation class TCRedisTest