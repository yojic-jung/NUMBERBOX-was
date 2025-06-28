//package com.kamcci.numberbox.infra.orm.jpa.adapter.mock
//
//import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRedisHash
//import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.member.MemberRedisRepository
//import java.util.*
//
//class MockMemberRedisRepository : MemberRedisRepository {
//    override fun findById(id: UUID): Optional<MemberRedisHash> {
//        TODO("Not yet implemented")
//    }
//
//    override fun findByEmail(email: String): Optional<MemberRedisHash> {
//        TODO("Not yet implemented")
//    }
//
//    override fun <S : MemberRedisHash?> save(entity: S & Any): S & Any {
//        TODO("Not yet implemented")
//    }
//
//    override fun <S : MemberRedisHash?> saveAll(entities: MutableIterable<S>): MutableIterable<S> {
//        TODO("Not yet implemented")
//    }
//
//    override fun existsById(id: UUID): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun findAll(): MutableIterable<MemberRedisHash> {
//        TODO("Not yet implemented")
//    }
//
//    override fun findAllById(ids: MutableIterable<UUID>): MutableIterable<MemberRedisHash> {
//        TODO("Not yet implemented")
//    }
//
//    override fun count(): Long {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteById(id: UUID) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteByEmail(email: String) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteAllById(id: List<UUID>) {
//        TODO("Not yet implemented")
//    }
//
//    override fun delete(entity: MemberRedisHash) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteAllById(ids: MutableIterable<UUID>) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteAll(entities: MutableIterable<MemberRedisHash>) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteAll() {
//        TODO("Not yet implemented")
//    }
//}