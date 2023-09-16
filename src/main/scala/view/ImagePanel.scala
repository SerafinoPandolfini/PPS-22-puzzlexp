package view

import java.awt.*
import javax.swing.*

/** a JPanel with a background image. The image is scaled in a way that maintain the original ratio and fit the panel
  * cutting the dimension (width or height) that is too large for the panel (if any)
  * @param image
  *   the [[Image]] to display in the background
  */
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
