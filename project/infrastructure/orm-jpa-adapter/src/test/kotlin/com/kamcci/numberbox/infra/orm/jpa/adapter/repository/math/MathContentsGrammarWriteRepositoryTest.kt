package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsGrammarWriteRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val mathContentsGrammarWriteRepository: MathContentsGrammarWriteRepository
) {

    @Test
    fun `수학문제 문법 저장`() {
        // given
        val contentsId = 4907L
        val grammar = ""

        // when
        mathContentsGrammarWriteRepository.createGrammar(contentsId, grammar)
        em.flush()
        em.clear()
    }

    @Test
    fun `수학문제 문법 수정`() {
        // given
        val contentsId = 1L
        val grammar = ""

        // when
        mathContentsGrammarWriteRepository.createGrammar(contentsId, grammar)
        em.flush()
        em.clear()
    }

}
