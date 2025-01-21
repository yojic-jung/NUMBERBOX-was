package com.kamcci.numberbox.app.domain.enumeration.hwp

/**
 * hwp 확장자 타입
 *
 * @param code hwp 서버에서 사용하는 확장자 코드 타입
 */
enum class HwpExtensionType(val code: Int) {
    HWP(1),
    HWPX(2),
    HWT(3),
    HWTX(4),
    HML(5),
}