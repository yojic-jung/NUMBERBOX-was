package com.kamcci.numberbox.app.port.orm.math

/**
 * 입시 문제 출처 - 조회
 */
interface MathContentsIpsiReadOrmPort {
    // 제공 중인 입시문제의 출제년도 조회
    fun readAllIpsiYear(): List<Int>
}