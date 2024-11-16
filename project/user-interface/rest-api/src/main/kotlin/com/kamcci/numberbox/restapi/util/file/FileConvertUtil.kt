package com.kamcci.numberbox.restapi.util.file

import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO

object FileConvertUtil {
    fun pptToImg(multipartFile: MultipartFile): List<InputStream> {
        val slideShow = XMLSlideShow(multipartFile.inputStream)
        val slides = slideShow.slides
        val imageFormat = "png"
        val imgList: MutableList<InputStream> = mutableListOf()

        for (slide in slides) {
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
            imgList.add(inputStream)
        }
        slideShow.close()

        return imgList
    }
}