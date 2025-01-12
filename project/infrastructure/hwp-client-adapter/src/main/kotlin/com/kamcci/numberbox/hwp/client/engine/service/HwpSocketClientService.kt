package com.kamcci.numberbox.hwp.client.engine.service

import com.kamcci.numberbox.app.domain.dto.hwp.HwpExtensionType
import com.kamcci.numberbox.app.domain.dto.hwp.HwpRequestType
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.hwp.client.engine.config.HwpSocketClientProperty
import org.springframework.stereotype.Service
import java.io.*
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder

@Service
class HwpSocketClientService(
    private val hwpSocketProp: HwpSocketClientProperty
) : HwpSocketClient {
    companion object {
        // hwp 서버에 파일 사이즈를 알려줄 영역의 크기
        const val HEADER_SIZE = 4

        // 입출력 파일 버퍼 크기
        const val BUFFER_SIZE = 8192
    }

    /**
     * jsonMsg to hwp 파일 변환 통신 규칙
     *
     * 1. 첫번째 4바이트는 파일크기
     * 2. json 문자열 바이트 형태로 전송
     */
    override fun requestHwpFile(jsonMsg: String): ByteArray {
        // 클라이언트 소켓 생성
        val hwpClientSocket = Socket(hwpSocketProp.ip, hwpSocketProp.port)
        val socketOup = DataOutputStream(hwpClientSocket.getOutputStream())
        val socketInp = hwpClientSocket.getInputStream()

        try {
            // 1. 요청 타입 및 데이터 크기 전송
            val data = jsonMsg.toByteArray()
            setRequestType(HwpRequestType.JsonToHwp, data.size, socketOup)

            // 2. 메시지 전송
            socketOup.write(data)
            socketOup.flush()

            // 3. hwp 서버에서 전송한 hwp 파일 반환
            return readSeverData(socketInp)
        } catch (e: IOException) {
            // 예외 발생 시 처리
            e.printStackTrace()
            throw IOException("hwp 서버 통신 중 오류 발생", e)
        } finally {
            socketInp.close()
            socketOup.close()
            hwpClientSocket.close()
        }
    }

    /**
     * hwp to html zip 파일 변환 통신 규칙
     *
     * 1. 첫번째 4바이트는 파일크기
     * 2. 두번째 4바이트는 파일 확장자
     * 3. 파일 컨텐츠
     */
    override fun requestHtmlZip(hwpFileIS: InputStream, extension: HwpExtensionType): ByteArray {
        // 클라이언트 소켓 생성
        val hwpClientSocket = Socket(hwpSocketProp.ip, hwpSocketProp.port)
        val socketOup = DataOutputStream(hwpClientSocket.getOutputStream())
        val socketInp = hwpClientSocket.getInputStream()

        try {
            // 1. 요청 타입 및 파일 크기 전송
            val byteBuffer = ByteArray(BUFFER_SIZE)
            val dataSize = socketInp.read(byteBuffer)
            setRequestType(HwpRequestType.HwpToHTML, dataSize, socketOup)

            // 2. 확장자 전송
            val extBufferSize = ByteBuffer.allocate(HEADER_SIZE)
            extBufferSize.order(ByteOrder.LITTLE_ENDIAN)
            extBufferSize.putInt(extension.code)
            socketOup.write(extBufferSize.array(), 0, HEADER_SIZE)

            // 3. hwp 서버에 파일 전송
            val buffer = ByteArray(BUFFER_SIZE)
            var bytesRead: Int
            while (socketInp.read(buffer).also { bytesRead = it } != -1) {
                socketOup.write(buffer, 0, bytesRead)
            }
            socketOup.flush()

            // 4. hwp 서버에서 반환한 zip 파일 byteArray로 반환
            return readSeverData(socketInp)
        } catch (e: Exception) {
            // 예외 발생 시 처리
            e.printStackTrace()
            throw IOException("hwp 서버 통신 중 오류 발생", e)
        } finally {
            socketInp.close()
            socketOup.close()
            hwpClientSocket.close()
        }
    }

    // 서버에 요청 타입 설정
    private fun setRequestType(requestType: HwpRequestType, byteSize: Int, socketOup: OutputStream) {
        val modeBuffer = ByteBuffer.allocate(HEADER_SIZE)
        modeBuffer.order(ByteOrder.LITTLE_ENDIAN)
        modeBuffer.put(requestType.type.toByteArray())
        socketOup.write(modeBuffer.array())

        // hwp 서버에 전송할 데이터 크기 전달
        val b = ByteBuffer.allocate(HEADER_SIZE)
        b.order(ByteOrder.LITTLE_ENDIAN)
        b.putInt(byteSize)
        socketOup.write(b.array())
        socketOup.flush()
    }

    // 서버에서 전달한 데이터 읽기
    private fun readSeverData(socketInp: InputStream): ByteArray {
        val buffer = ByteArrayOutputStream()
        val bufferByte = ByteArray(BUFFER_SIZE) // 읽을 바이트의 버퍼 크기 설정
        var bytesRead: Int
        while (socketInp.read(bufferByte).also { bytesRead = it } != -1) {
            buffer.write(bufferByte, 0, bytesRead)
        }
        return buffer.toByteArray()
    }
}