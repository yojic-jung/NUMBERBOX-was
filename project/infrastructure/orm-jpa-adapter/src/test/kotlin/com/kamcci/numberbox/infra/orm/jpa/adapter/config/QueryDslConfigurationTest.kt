package com.kamcci.numberbox.infra.orm.jpa.adapter.config

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class QueryDslConfigurationTest {

    @Test
    fun `QueryDslConfig 설정 - 성공`() {
        // given
        val entityManager = Mockito.mock(EntityManager::class.java)
        val queryDslConfiguration = QueryDslConfiguration(entityManager)

        // when
        val jpaQueryFactory = queryDslConfiguration.jpaQueryFactory()

        // then
        assertThat(jpaQueryFactory).isNotNull
    }
}