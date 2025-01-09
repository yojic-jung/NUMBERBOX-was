package com.kamcci.numberbox.infra.orm.jpa.adapter.base

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired

abstract class BaseRepository {
    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var queryFactory: JPAQueryFactory
}
