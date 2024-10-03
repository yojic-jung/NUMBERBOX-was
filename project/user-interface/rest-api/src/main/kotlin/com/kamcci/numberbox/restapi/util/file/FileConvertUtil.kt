package com.kamcci.numberbox.restapi.util.file

import org.springframework.web.multipart.MultipartFile
import java.io.File

object FileConvertUtil {
    fun toFile(multipartFile: MultipartFile): File {
        val imgFile = File(multipartFile.originalFilename!!)
        multipartFile.transferTo(imgFile)
        return imgFile
    }
}