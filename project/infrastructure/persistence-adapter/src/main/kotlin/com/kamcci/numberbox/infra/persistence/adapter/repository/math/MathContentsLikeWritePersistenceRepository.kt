package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeWriteRepository
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsLikeRedisRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class MathContentsLikeWritePersistenceRepository(
    private val mathContentsLikeRedisRepository: MathContentsLikeRedisRepository,
    private val mathContentsLikeWriteRepository: MathContentsLikeWriteRepository
) : MathContentsLikeWriteOrmPort {
    override fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        // redis 캐싱(rdb는 스케줄러 통해 저장됨)
        return mathContentsLikeRedisRepository.save(modifyDto)
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        // redis 캐시 삭제
        mathContentsLikeRedisRepository.delete(modifyDto)

        // rdb 즉시 삭제
        return mathContentsLikeWriteRepository.delete(modifyDto)
    }

}