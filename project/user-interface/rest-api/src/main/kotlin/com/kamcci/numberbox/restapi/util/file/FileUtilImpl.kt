package com.kamcci.numberbox.restapi.util.file

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO

@Component
class FileUtilImpl : FileUtil {
    // multipartFile to inputStream
    override fun toFile(multipartFile: MultipartFile): FileUploadDto {
        return FileUploadDto(
            multipartFile.originalFilename!!,
            multipartFile.contentType,
            multipartFile.size,
            multipartFile.inputStream
        )
    }

    // ppt 슬라이드별 inputStream 변환
    override fun toPptSlide(pptInp: InputStream): List<FileUploadDto> {
        val slideShow = XMLSlideShow(pptInp)
        val slides = slideShow.slides
        val imageFormat = "png"
        val imgList: MutableList<FileUploadDto> = mutableListOf()

        for ((idx, slide) in slides.withIndex()) {
            // 슬라이드를 BufferedImage로 변환
            val image = BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB) // 필요한 해상도 설정
            val graphics = image.createGraphics()
            slide.draw(graphics)
            graphics.dispose()

            // BufferedImage를 ByteArray로 변환
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(image, imageFormat, outputStream)
            val imageBytes = outputStream.toByteArray()
            outputStream.close()

            val inputStream = ByteArrayInputStream(imageBytes)
            imgList.add(
                FileUploadDto(
                    "slide_$idx.$imageFormat", // 고유한 파일 이름을 슬라이드 인덱스로 설정
                    "image/$imageFormat", // contentType을 명시적으로 설정
                    imageBytes.size.toLong(),
                    inputStream
                )
            )
        }
        slideShow.close()

        return imgList
    }

}