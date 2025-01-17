package com.kamcci.numberbox.hwp.client.engine.service

import com.kamcci.numberbox.hwp.client.engine.config.HwpSocketClientProperty
import org.springframework.stereotype.Service
import java.net.Socket

@Service
class HwpClientSocketFactory(
    private val hwpSocketProp: HwpSocketClientProperty
) : SocketFactory {
    override fun getSocket(): Socket {
        return Socket(hwpSocketProp.ip, hwpSocketProp.port)
    }
}