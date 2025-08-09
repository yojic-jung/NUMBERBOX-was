package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeReadRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeWriteRepository
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsLikeRedisRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class MathContentsLikeWritePersistenceRepository(
    private val mathContentsLikeRedisRepository: MathContentsLikeRedisRepository,
    private val mathContentsLikeWriteRepository: MathContentsLikeWriteRepository,
    private val mathContentsLikeReadRepository: MathContentsLikeReadRepository,
) : MathContentsLikeWriteOrmPort {
    override fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        // todo
        // redis 캐싱(rdb는 스케줄러 통해 저장됨)
        // 1. userId 하위 contentsId(유저가 누른 전체 좋아요 컨텐츠 id)


        // 2. contentsId 하위 userId 저장(컨텐츠에 신규로 좋아요 누른 사용자 목록)


        // 3. likeCount+1

        return mathContentsLikeRedisRepository.save(modifyDto)
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        // redis 캐시 삭제
        mathContentsLikeRedisRepository.delete(modifyDto)

        // rdb 즉시 삭제
        return mathContentsLikeWriteRepository.delete(modifyDto)
    }

}