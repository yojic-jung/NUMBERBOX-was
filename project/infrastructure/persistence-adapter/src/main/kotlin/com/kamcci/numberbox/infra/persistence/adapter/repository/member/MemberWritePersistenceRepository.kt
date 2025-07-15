package com.kamcci.numberbox.infra.persistence.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberWriteRepository
import com.kamcci.numberbox.infra.redis.adapter.repository.member.MemberRedisRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Primary
@Repository
class MemberWritePersistenceRepository(
    @Qualifier("jpaAdapter")
    private val memberWritePersistenceRepository: MemberWriteOrmPort,
    private val memberRedisRepository: MemberRedisRepository
) : MemberWriteOrmPort, BaseRepository() {
    override fun save(email: String, password: String): UUID {
        return memberWritePersistenceRepository.save(email, password)
    }

    override fun drop(memberId: UUID): Long {
        // redis 캐싱 삭제
        memberRedisRepository.deleteById(memberId)

        // rdb 즉시 삭제
        return memberWritePersistenceRepository.drop(memberId)
    }

    override fun updatePassword(memberId: UUID, password: String): Long {
        // redis 캐싱 삭제
        memberRedisRepository.deleteById(memberId)

        // rdb 즉시 수정
        return memberWritePersistenceRepository.updatePassword(memberId, password)
    }

    override fun updatePassword(memberId: List<UUID>, password: String?): Long {
        // redis 캐싱 삭제
        memberRedisRepository.deleteAllById(memberId)

        // rdb 즉시 수정
        return memberWritePersistenceRepository.updatePassword(memberId, password)
    }

    override fun updatePassword(email: String, password: String): Long {
        // redis 캐싱 삭제
        memberRedisRepository.findByEmail(email)?.let {
            memberRedisRepository.deleteById(it.id)
        }

        // rdb 즉시 삭제
        return memberWritePersistenceRepository.updatePassword(email, password)
    }

    override fun updateFailCountById(userId: UUID, failCount: Int): Long {
        return memberWritePersistenceRepository.updateFailCountById(userId, failCount)
    }

    override fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Long {
        return memberWritePersistenceRepository.updateLastFailTimeById(userId, lastFailTime)
    }
}