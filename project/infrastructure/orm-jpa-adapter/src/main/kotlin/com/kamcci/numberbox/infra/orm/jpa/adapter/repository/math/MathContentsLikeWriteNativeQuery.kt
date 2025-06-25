package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class MathContentsLikeWriteNativeQuery : BaseRepository() {
    @Transactional
    fun bulkInsertLikes(contentsId: Long, memberIdList: List<String>) {
        if (memberIdList.isEmpty()) return

        val sql = buildString {
            append("INSERT IGNORE INTO math_con_like (contents_id, member_id) VALUES ")
            append(
                memberIdList.joinToString(", ") {
                    val hexUuid = it.toString().replace("-", "")
                    "($contentsId, UNHEX('$hexUuid'))"
                }
            )
        }

        em.createNativeQuery(sql).executeUpdate()
    }

    @Transactional
    fun bulkInsertRepo(contentsId: Long, memberIdList: List<String>) {
        if (memberIdList.isEmpty()) return

        val sql = buildString {
            append("INSERT IGNORE INTO math_con_repo (contents_id, member_id, sys_create_date) VALUES ")
            append(
                memberIdList.joinToString(", ") {
                    val hexUuid = it.toString().replace("-", "")
                    "($contentsId, UNHEX('$hexUuid'), NOW())"
                }
            )
        }

        em.createNativeQuery(sql).executeUpdate()
    }
}