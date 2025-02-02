package com.kamcci.numberbox.restapi.dto.response.common

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PageResponseImplTest {
    @Test
    fun `empty 컨텐츠 페이징 - 성공`() {
        // given
        val contents: List<String> = listOf()
        val pageReq = PageRequestImpl(0, 5)
        val countFunction: () -> Long = { 0 }
        // when
        val pageRes = paginate(contents, pageReq, countFunction)

        // then
        assertThat(pageRes.contents).isEqualTo(contents)
        assertThat(pageRes.page).isEqualTo(pageReq)
        assertThat(pageRes.total).isEqualTo(countFunction())
    }

    @Test
    fun `컨텐츠가 조회 기준 보다 작은 경우 - 성공`() {
        // given
        val contents = listOf("", "")
        val pageReq = PageRequestImpl(0, 5)
        val countFunction: () -> Long = { 0 }
        // when
        val pageRes = paginate(contents, pageReq, countFunction)

        // then
        assertThat(pageRes.contents).isEqualTo(contents)
        assertThat(pageRes.page).isEqualTo(pageReq)
        assertThat(pageRes.total).isNotEqualTo(countFunction())
    }

    @Test
    fun `컨텐츠가 조회 기준 보다 큰 경우 - 성공`() {
        // given
        val contents = listOf("", "", "", "", "", "")
        val pageReq = PageRequestImpl(0, 5)
        val countFunction: () -> Long = { 0 }
        // when
        val pageRes = paginate(contents, pageReq, countFunction)

        // then
        assertThat(pageRes.contents).isEqualTo(contents)
        assertThat(pageRes.page).isEqualTo(pageReq)
        assertThat(pageRes.total).isEqualTo(countFunction())
    }
}