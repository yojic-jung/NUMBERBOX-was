package com.kamcci.numberbox.restapi.dto.response.common

import com.kamcci.numberbox.app.domain.dto.common.PageRequest


data class PageResponseImpl<T>(
    override val contents: List<T>,
    override val page: PageRequest,
    override val total: Long,
) : PageResponse<T> {

    companion object {
        fun <T> paginate(
            contents: List<T>,
            page: PageRequest,
            countFunction: () -> Long,
        ): PageResponseImpl<T> {
            // 컨텐츠 사이즈가 페이징 사이즈 보다 작은 경우 카운트 함수 실행 안함
            return if (contents.isNotEmpty() && contents.size < page.pageVolume) {
                PageResponseImpl(contents, page, page.getOffset() + contents.size.toLong())
            } else {
                PageResponseImpl(contents, page, countFunction())
            }
        }
    }
}
