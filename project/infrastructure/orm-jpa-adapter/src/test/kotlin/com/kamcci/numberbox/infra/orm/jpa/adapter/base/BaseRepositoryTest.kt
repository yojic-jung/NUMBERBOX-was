package com.kamcci.numberbox.infra.orm.jpa.adapter.base

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock

class BaseRepositoryTest {
    private lateinit var testRepository: TestRepository

    @Test
    fun `EntityManager 조회 - 실패(초기화 미진행)`() {
        // given
        testRepository = TestRepository()

        // when & then
        assertThrows<UninitializedPropertyAccessException> {
            testRepository.getEm4Test()
        }
    }

    @Test
    fun `JPAQueryFactory 조회 - 실패(초기화 미진행)`() {
        // given
        testRepository = TestRepository()

        // when & then
        assertThrows<UninitializedPropertyAccessException> {
            testRepository.getQueryFactory4Test()
        }
    }

    @Test
    fun `EntityManager, JPAQueryFactory 조회 - 성공`() {
        // given
        testRepository = TestRepository()
        testRepository.setEm4Test(mock())
        testRepository.setQueryFactory4Test(mock())

        // when & then
        assertDoesNotThrow {
            testRepository.getEm4Test()
            testRepository.getQueryFactory4Test()
        }
    }
}

class TestRepository : BaseRepository() {
    fun getEm4Test() = em
    fun getQueryFactory4Test() = queryFactory

    fun setEm4Test(em: EntityManager) {
        this.em = em
    }

    fun setQueryFactory4Test(queryFactory: JPAQueryFactory) {
        this.queryFactory = queryFactory
    }
}