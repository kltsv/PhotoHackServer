package com.ringov

import java.awt.Color
import java.awt.GradientPaint
import java.awt.Graphics2D
import java.awt.Rectangle
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage


class ImageModifier {
    fun modify(file: File): ModifiedImage {
        val image = getImage(file)
        val rectangle = getRectangle(image)
        val graphics = getGraphic(image)
        draw(graphics, rectangle)
        graphics.dispose()
        return ModifiedImage(image)
    }

    private fun getRectangle(image: BufferedImage): Rectangle = Rectangle(0, 0, image.width, image.height)

    private fun draw(graphics: Graphics2D, rectangle: Rectangle) {
        val blackOpacityColor = Color(0, 0, 0, 90)
        drawForegroundGradient(graphics, rectangle, blackOpacityColor)

    }

    private fun drawForegroundGradient(graphics: Graphics2D, rectangle: Rectangle, color: Color) {
        val bottomRectLine = 0.4
        val bottomRect = Rectangle(rectangle.x, (rectangle.height - (rectangle.height * bottomRectLine)).toInt(),
                rectangle.width, (rectangle.height * bottomRectLine).toInt())
        val gradientRect = Rectangle(bottomRect.x, bottomRect.y, bottomRect.x, rectangle.height)
        graphics.fillGradientRect(buildLinearGradient(gradientRect, color), bottomRect)
    }

    private fun buildLinearGradient(rect: Rectangle, color: Color) =
            GradientPaint(rect.x.toFloat(), rect.y.toFloat(),
                    Color(color.red, color.green, color.blue, 0), rect.x.toFloat(),
                    rect.height.toFloat(), color)

    private fun getGraphic(image: BufferedImage): Graphics2D = image.createGraphics()

    private fun getImage(file: File): BufferedImage = ImageIO.read(file)

    class ModifiedImage(private val image: BufferedImage) {
        fun writeToFile(file: File) {
            ImageIO.write(image, file.extension, file)
        }
    }

    private fun Graphics2D.fillGradientRect(gradient: GradientPaint, rect: Rectangle) {
        val paintTmp = this.paint
        this.paint = gradient
        fillRect(rect.x, rect.y, rect.width, rect.height)
        this.paint = paintTmp
    }

    private fun Graphics2D.fillColoredRect(color: Color, rect: Rectangle) {
        val colorTmp = this.color
        this.color = color
        fillRect(rect.x, rect.y, rect.width, rect.height)
        this.color = colorTmp
    }
}