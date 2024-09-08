package com.numberbox.infra.orm_adpater.abstract_code

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired

abstract class CommonRepository {
    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var queryFactory: JPAQueryFactory
}
