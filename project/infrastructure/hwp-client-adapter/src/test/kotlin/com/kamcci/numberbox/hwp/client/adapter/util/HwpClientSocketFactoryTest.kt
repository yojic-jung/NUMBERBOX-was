package com.kamcci.numberbox.hwp.client.adapter.util

import com.kamcci.numberbox.hwp.client.adapter.config.HwpSocketClientProperty
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.ConnectException
import java.net.ServerSocket

class HwpClientSocketFactoryTest {
    companion object {
        const val LOCAL_IP = "127.0.01"
        const val SOCKET_PORT = 5555
    }

    lateinit var socketFactory: HwpClientSocketFactory

    @BeforeEach
    fun `소켓 초기화`() {
        val clientProp = HwpSocketClientProperty(LOCAL_IP, SOCKET_PORT)
        socketFactory = HwpClientSocketFactory(clientProp)
    }


    @Test
    fun `소켓 생성 - 성공`() {
        // 클라이언트와 연결할 서버 소켓 생성
        val serverSocket = ServerSocket(SOCKET_PORT)

        // when
        val clientSocket = socketFactory.getSocket()

        // then
        assertNotNull(clientSocket)
        assertTrue(clientSocket.isConnected)

        // 후처리 - 서버소켓 제거
        serverSocket.close()
    }

    @Test
    fun `소켓 생성 - 실패(미존재 port)`() {
        // when & then
        assertThrows<ConnectException> {
            socketFactory.getSocket()
        }
    }
}