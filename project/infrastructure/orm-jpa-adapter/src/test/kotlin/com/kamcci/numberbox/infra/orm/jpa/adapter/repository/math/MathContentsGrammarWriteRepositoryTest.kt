package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConGrammarDummyFactory.NOT_SAVED_CONTENTS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConGrammarDummyFactory.getMathConGrammarDummyEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsGrammarWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val mathContentsGrammarWriteRepository: MathContentsGrammarWriteRepository
) {

    @Test
    fun `수학문제 문법 저장`() {
        // given - 아직 저장하지 않은 컨텐츠 id
        val notSavedContentId = NOT_SAVED_CONTENTS_ID
        val grammar = "any"

        // when
        val isUpdated = mathContentsGrammarWriteRepository.update(notSavedContentId, grammar)
        em.flush()
        em.clear()

        // then - 수정 아닌 신규 저장
        assertThat(isUpdated).isFalse()
    }

    @Test
    fun `수학문제 문법 수정`() {
        // given - 이미 저장한 컨텐츠 id
        val contentsId = getMathConGrammarDummyEntity().contentsId
        val grammar = "any"

        // when
        val isUpdated = mathContentsGrammarWriteRepository.update(contentsId, grammar)
        em.flush()
        em.clear()

        // then - 수정 성공
        assertThat(isUpdated).isTrue()
    }

}
