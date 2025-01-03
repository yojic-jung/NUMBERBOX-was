package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.usecase.math.MathContentsGrammarWriteCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsGrammarEntity
import org.springframework.stereotype.Repository

@Repository
class MathContentsGrammarWriteRepository : MathContentsGrammarWriteCase, BaseRepository() {

    override fun createGrammar(contentsId: Long, grammar: String): Boolean {
        val existEntity = em.find(MathContentsGrammarEntity::class.java, contentsId)

        val saveEntity =
            if (existEntity != null) { // 엔티티 존재시 수정
                existEntity.grammar = grammar
                existEntity
            } else { // 엔티티 미존재시 생성
                MathContentsGrammarEntity().apply {
                    this.contentsId = contentsId
                    this.grammar = grammar
                }
            }

        em.persist(saveEntity)
        return existEntity != null
    }

}