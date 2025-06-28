package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsRepoWriteRepository
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsRepoRedisRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class MathContentsRepoWriteRedisRepository(
    private val mathContentsRepoRedisRepository: MathContentsRepoRedisRepository,
    private val mathContentsRepoWriteRepository: MathContentsRepoWriteRepository
) : MathContentsRepoWriteOrmPort {
    override fun save(modifyDto: MathContentsRepoModifyDto): Boolean {
        // redis 캐싱(rdb는 스케줄러 통해 저장됨)
        return mathContentsRepoRedisRepository.save(modifyDto)
    }

    override fun delete(modifyDto: MathContentsRepoModifyDto): Long {
        // redis 삭제
        mathContentsRepoRedisRepository.delete(modifyDto)

        // rdb 즉시 삭제
        return mathContentsRepoWriteRepository.delete(modifyDto)
    }
}