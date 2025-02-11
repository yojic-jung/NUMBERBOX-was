package com.kamcci.numberbox.hwp.client.adapter.service

import java.net.Socket

interface SocketFactory {
    fun getSocket(): Socket
}