package com.kamcci.numberbox.app.usecase.math

/**
 * 입시 문제 출처 - 조회
 */
interface MathContentsIpsiReadCase {

    // 제공 중인 입시문제의 출제년도 조회
    fun readAllIpsiYear(): List<Int>
}