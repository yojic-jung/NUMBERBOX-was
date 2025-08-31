package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertFileCreateDto
import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.hwp.HwpConvertFileDummyFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class HwpConvertFileWriteRepositoryTest @Autowired constructor(
    private val hwpConvertFileWriteRepository: HwpConvertFileWriteRepository
) : BaseRepository() {
    @Test
    fun `변환 컨텐츠 저장 - 성공`() {
        // given
        val createDto = HwpConvertFileCreateDto(
            memberId = MemberDummyFactory.getMemberDummyEntity().memberId,
            convertType = HwpConvertFileType.JsonToHwp,
            originFileName = "anyFileName",
        )

        // when
        val id = hwpConvertFileWriteRepository.create(createDto)
        em.flush()
        em.clear()

        // then
        Assertions.assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `변환 컨텐츠 수정 - 성공`() {
        // given
        val existEntity = HwpConvertFileDummyFactory.getHwpConvertFileDummyEntity()

        // when
        val executeRowCnt = hwpConvertFileWriteRepository.update(existEntity.id, "any")

        // then
        Assertions.assertThat(executeRowCnt).isOne()
    }
}