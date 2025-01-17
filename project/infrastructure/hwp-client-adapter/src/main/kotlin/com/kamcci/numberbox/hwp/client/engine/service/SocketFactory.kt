package com.kamcci.numberbox.hwp.client.engine.service

import java.net.Socket

interface SocketFactory {
    fun getSocket(): Socket
}