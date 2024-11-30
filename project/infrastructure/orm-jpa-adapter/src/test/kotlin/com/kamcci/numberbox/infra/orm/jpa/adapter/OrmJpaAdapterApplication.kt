package com.kamcci.numberbox.infra.orm.jpa.adapter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.stereotype.Repository


@ComponentScan(
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [Repository::class]
        )
    ]
)
@SpringBootApplication
class OrmJpaAdapterApplication