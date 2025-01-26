package com.kamcci.numberbox.restapi.util.hwp

import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

@Component
class HwpConvertFileUtil {
    /**
     * hwp to html 변환 zip파일 압축 해제
     *
     * 아래 구조로 이루어진 zip 파일
     * - index.xhtml
     * - bindata
     *      - *.png
     */
    fun unzip(zipBytes: ByteArray): Pair<String?, Map<String, ByteArray>> {
        var indexXhtml: String? = null
        val images = mutableMapOf<String, ByteArray>()

        // ByteArrayInputStream으로 byteArray 읽기
        val byteArrayInputStream = ByteArrayInputStream(zipBytes)

        // ZipInputStream으로 압축 해제
        ZipInputStream(byteArrayInputStream).use { zipInputStream ->
            var entry: ZipEntry? = zipInputStream.nextEntry
            while (entry != null) {
                val entryName = entry.name

                if (!entry.isDirectory) {
                    // /bindata/ 하위 이미지 파일 읽기
                    if (entryName.startsWith("bindata/")) {
                        val fileName = entryName.removePrefix("bindata/")
                        images[fileName] = zipInputStream.readBytes()
                    }
                    // index.xhtml 파일 읽기
                    if (entryName.equals("index.xhtml", ignoreCase = true)) {
                        indexXhtml = zipInputStream.readBytes().toString(Charsets.UTF_8)
                    }
                }

                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry
            }
        }
        return indexXhtml to images
    }
}