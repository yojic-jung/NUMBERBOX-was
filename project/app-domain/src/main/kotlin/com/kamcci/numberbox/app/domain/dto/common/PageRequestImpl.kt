package com.kamcci.numberbox.app.domain.dto.common

class PageRequestImpl(
    override val num: Long,
    override val volume: Long
) : PageRequest