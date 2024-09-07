package com.numberbox.infra.orm_adpater.abstract_code

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired

abstract class CommonRepository {
    @Autowired
    lateinit var em: EntityManager
}