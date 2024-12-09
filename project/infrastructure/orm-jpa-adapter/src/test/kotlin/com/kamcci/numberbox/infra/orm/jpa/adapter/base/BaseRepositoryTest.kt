//package com.kamcci.numberbox.infra.orm.jpa.adapter.base
//
//import com.querydsl.jpa.impl.JPAQueryFactory
//import jakarta.persistence.EntityManager
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.mockito.Mockito.mock
//
//class BaseRepositoryTest {
//    private val entityManager: EntityManager = mock()
//
//    private val queryFactory: JPAQueryFactory = mock()
//
//    private lateinit var testRepository: TestRepository
//
//    @Test
//    fun `BaseRepository 설정 - 성공`() {
//        // given
//        testRepository = TestRepository()
//        testRepository.em = entityManager
//        testRepository.queryFactory = queryFactory
//
//        // then
//        assertThat(testRepository.em).isEqualTo(entityManager)
//        assertThat(testRepository.queryFactory).isEqualTo(queryFactory)
//    }
//
//    @Test
//    fun `BaseRepository  설정 - 실패`() {
//        // given
//        testRepository = TestRepository()
//
//        // then
//        assertThrows<UninitializedPropertyAccessException> {
//            testRepository.em
//        }
//        assertThrows<UninitializedPropertyAccessException> {
//            testRepository.queryFactory
//        }
//    }
//}
//
//class TestRepository : BaseRepository()