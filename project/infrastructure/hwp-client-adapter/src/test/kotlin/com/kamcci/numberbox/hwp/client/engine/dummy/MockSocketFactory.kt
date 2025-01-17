package com.kamcci.numberbox.hwp.client.engine.dummy

import com.kamcci.numberbox.hwp.client.engine.service.SocketFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.Socket

object MockSocketFactory {

    fun getSocketFactory(response: String): SocketFactory = object : SocketFactory {
        override fun getSocket(): Socket {
            return StubSocket()
        }

        inner class StubSocket : Socket() {
            private val outputStream = ByteArrayOutputStream()
            private val inputStream = ByteArrayInputStream(response.toByteArray())

            override fun getInputStream() = inputStream
            override fun getOutputStream() = outputStream

        }
    }

    fun getErrorSocketFactory(response: String): SocketFactory = object : SocketFactory {
        override fun getSocket(): Socket {
            return StubSocket()
        }

        inner class StubSocket : Socket() {
            private val outputStream = ByteArrayOutputStream()
            private val inputStream = ByteArrayInputStream(response.toByteArray())

            override fun getInputStream(): InputStream {
                throw RuntimeException("")
            }

            override fun getOutputStream() = outputStream

        }
    }
}