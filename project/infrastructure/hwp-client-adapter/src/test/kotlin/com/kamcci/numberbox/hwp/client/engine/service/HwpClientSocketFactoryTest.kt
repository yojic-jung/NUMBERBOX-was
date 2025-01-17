package com.kamcci.numberbox.hwp.client.engine.service

import com.kamcci.numberbox.hwp.client.engine.config.HwpSocketClientProperty
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.ConnectException
import java.net.ServerSocket

class HwpClientSocketFactoryTest {
    @Test
    fun `소켓 생성 - 성공`() {
        val port = 5555
        val serverSocket = ServerSocket(port)
        val clientProp = HwpSocketClientProperty("127.0.0.1", port)
        val socketFactory = HwpClientSocketFactory(clientProp)

        val clientSocket = socketFactory.getSocket()
        assertNotNull(clientSocket)
        assertTrue(clientSocket.isConnected)

        serverSocket.close()
    }

    @Test
    fun `소켓 생성 - 실패(미존재 port)`() {
        val clientProp = HwpSocketClientProperty("127.0.0.1", 5555)
        val socketFactory = HwpClientSocketFactory(clientProp)

        assertThrows<ConnectException> {
            socketFactory.getSocket()
        }
    }
}