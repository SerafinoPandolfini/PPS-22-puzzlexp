package view

import utils.DisplayValuesManager

import java.awt.*
import javax.swing.*

class ImagePanel(image: Image) extends JPanel {

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)

    val g2d = g.asInstanceOf[Graphics2D]

    val scaleX = getWidth.toDouble / image.getWidth(null)
    val scaleY = getHeight.toDouble / image.getHeight(null)

    val scale = Math.max(scaleX, scaleY)

    val newWidth = (image.getWidth(null) * scale).toInt
    val newHeight = (image.getHeight(null) * scale).toInt

    val x = (getWidth - newWidth) / 2
    val y = (getHeight - newHeight) / 2

    g2d.drawImage(
      image,
      x,
      y,
      newWidth,
      newHeight,
      null
    )
  }
}
