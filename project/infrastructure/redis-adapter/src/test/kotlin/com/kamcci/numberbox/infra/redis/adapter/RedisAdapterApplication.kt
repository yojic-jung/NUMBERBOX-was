package com.kamcci.numberbox.infra.redis.adapter

import com.kamcci.numberbox.infra.redis.adapter.config.RedisConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType


@ComponentScan(
    excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [RedisConfig::class])]
)
@SpringBootApplication
class RedisAdapterApplication