package com.kamcci.numberbox.app.domain.dto.hwp

/**
 * hwp 확장자 타입
 *
 * @param code hwp 서버에서 사용하는 확장자 코드 타입
 */
enum class HwpExtensionType(val code: Int) {
    Hwp(1),
    Hwpx(2),
    Hwt(3),
    Hwtx(4),
    Hml(5),
}