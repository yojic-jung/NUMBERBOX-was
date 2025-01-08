package com.kamcci.numberbox.infra.orm.jpa.adapter.base

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired

abstract class BaseRepository {
    @Autowired
    protected lateinit var em: EntityManager

    @Autowired
    protected lateinit var queryFactory: JPAQueryFactory
}
