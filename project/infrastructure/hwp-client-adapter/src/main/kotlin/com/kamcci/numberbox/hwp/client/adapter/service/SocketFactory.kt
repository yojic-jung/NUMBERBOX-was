package com.kamcci.numberbox.hwp.client.adapter.service

import java.net.Socket

/**
 * 소켓 생성 팩토리
 */
interface SocketFactory {
    fun getSocket(): Socket
}