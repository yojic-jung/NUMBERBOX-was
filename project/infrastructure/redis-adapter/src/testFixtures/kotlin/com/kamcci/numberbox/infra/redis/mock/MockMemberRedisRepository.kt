package com.kamcci.numberbox.infra.redis.mock

import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRedisHash
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRoleRedis
import com.kamcci.numberbox.infra.redis.adapter.repository.member.MemberRedisRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MockMemberRedisRepository : MemberRedisRepository {
    var executeCnt = 0

    val dataStore = ConcurrentHashMap<UUID, MemberRedisHash>()

    override fun findByEmail(email: String): MemberRedisHash? {
        executeCnt++
        return dataStore.values.find { it.email == email }
    }

    override fun deleteById(id: UUID) {
        executeCnt++
        dataStore.remove(id)
    }

    override fun deleteAllById(ids: Iterable<UUID>) {
        executeCnt++
        ids.forEach { dataStore.remove(it) }
    }

    override fun <S : MemberRedisHash?> save(entity: S & Any): S & Any {
        executeCnt++
        dataStore[entity.id] = entity
        return entity
    }

    override fun <S : MemberRedisHash?> saveAll(entities: Iterable<S?>): Iterable<S?> {
        executeCnt++
        entities.filterNotNull().forEach { save(it) }
        return entities
    }

    override fun findById(id: UUID): Optional<MemberRedisHash> {
        executeCnt++
        return Optional.ofNullable(dataStore[id])
    }

    override fun existsById(id: UUID): Boolean {
        executeCnt++
        return dataStore.containsKey(id)
    }

    override fun findAll(): Iterable<MemberRedisHash?> {
        executeCnt++
        return dataStore.values
    }

    override fun findAllById(ids: Iterable<UUID?>): Iterable<MemberRedisHash?> {
        executeCnt++
        return ids.mapNotNull { it?.let { id -> dataStore[id] } }
    }

    override fun count(): Long {
        executeCnt++
        return dataStore.size.toLong()
    }

    override fun delete(entity: MemberRedisHash) {
        executeCnt++
        dataStore.remove(entity.id)
    }

    override fun deleteAll(entities: Iterable<MemberRedisHash?>) {
        executeCnt++
        entities.filterNotNull().forEach { delete(it) }
    }

    override fun deleteAll() {
        executeCnt++
        dataStore.clear()
    }
}
