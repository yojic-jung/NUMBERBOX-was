package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.port.orm.math.MathContentsGrammarModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.MathContentsGrammarEntity
import org.springframework.stereotype.Repository

@Repository
class MathContentsGrammarModifyOrmAdapter : MathContentsGrammarModifyOrmPort, BaseRepository() {

    override fun createGrammar(contentsId: Long, grammar: String) {
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
    }

}