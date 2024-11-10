package com.kamcci.numberbox.app.domain.dto.common

class PageRequestImpl(
    override val pageNum: Long,
    override val pageVolume: Long
) : PageRequest {
    init {
        require(pageNum >= 0) { "페이지 번호는 0이상 이어야 합니다." }
        require(pageVolume > 0) { "페이지 사이즈는 0보다 커야 합니다." }
    }
}