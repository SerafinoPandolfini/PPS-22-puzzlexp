package view

import view.MultiLayeredTile.{StartX, StartY}
import java.awt.{GridLayout, Image, Graphics, Graphics2D, Dimension}
import javax.swing.*

/**
 * a graphic tile representation composed by ground, item and player
 */
class MultiLayeredTile() extends JComponent:
  var groundImage: Option[Image] = None
  var itemImage: Option[Image] = None
  var playerImage: Option[Image] = None

  override def paintComponent(graphics: Graphics): Unit =
    super.paintComponent(graphics)

    groundImage.foreach(image => graphics.drawImage(image, StartX, StartY, getWidth, getHeight, this))
    itemImage.foreach(image => graphics.drawImage(image, StartX, StartY, getWidth, getHeight, this))
    playerImage.foreach(image => graphics.drawImage(image, StartX, StartY, getWidth, getHeight, this))

object MultiLayeredTile:
  val StartX = 0
  val StartY = 0
