package com.kamcci.numberbox.hwp.client.adapter.mock

import com.kamcci.numberbox.hwp.client.adapter.util.SocketFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
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

    fun getErrorSocketFactory(): SocketFactory = object : SocketFactory {
        override fun getSocket(): Socket {
            return StubSocket()
        }

        inner class StubSocket : Socket() {
            override fun getInputStream() = MockInputStream()

            override fun getOutputStream() = MockOutputStream()

        }

        inner class MockOutputStream : OutputStream() {
            override fun write(b: Int) {
                throw RuntimeException()
            }

            override fun write(b: ByteArray) {
                throw RuntimeException()
            }
        }

        inner class MockInputStream : InputStream() {
            override fun read(): Int {
                throw RuntimeException()
            }

            override fun read(b: ByteArray): Int {
                throw RuntimeException()

            }
        }
    }
}