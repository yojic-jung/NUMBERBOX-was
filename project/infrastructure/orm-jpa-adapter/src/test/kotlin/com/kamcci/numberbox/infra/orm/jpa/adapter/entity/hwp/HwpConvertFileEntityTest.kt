package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.util.*

@TcDBJpaTest
class HwpConvertFileEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `HwpConvertFileEntity 조회`() {
        val entity = em.find(HwpConvertFileEntity::class.java, 2L)

        assertThat(entity.id).isNotNull()
        assertThat(entity.memberId).isNotNull()
        assertThat(entity.convertType).isNotNull()
        assertThat(entity.originFileName).isNotNull()
        assertThat(entity.convertFileName).isNotNull()
        assertThat(entity.requestAt).isNotNull()
        assertThat(entity.convertAt).isNotNull()
        assertThat(entity.deletedAt).isNotNull()
    }

    @Test
    fun `HwpConvertFileEntity 생성`() {
        val now = LocalDateTime.now()
        val saveEntity = HwpConvertFileEntity().apply {
            memberId = UUID.randomUUID()
            convertType = HwpConvertFileType.JsonToHwp
            originFileName = ""
            convertFileName = ""
            requestAt = now
            convertAt = now
            deletedAt = now
        }

        em.persist(saveEntity)
    }
}