package view.game

import utils.constants.ImageManager
import view.game.MultiLayeredTile.{BackgroundImage, StartX, StartY}

import java.awt.*
import javax.swing.*

/** a graphic tile representation composed by ground, item and player
  */
class MultiLayeredTile() extends JComponent:
  var cellImage: Option[Image] = None
  var itemImage: Option[Image] = None
  var playerImage: Option[Image] = None

  override def paintComponent(graphics: Graphics): Unit =
    super.paintComponent(graphics)
    graphics.drawImage(BackgroundImage, StartX, StartY, getWidth, getHeight, this)
    cellImage.foreach(image => graphics.drawImage(image, StartX, StartY, getWidth, getHeight, this))
    itemImage.foreach(image => graphics.drawImage(image, StartX, StartY, getWidth, getHeight, this))
    playerImage.foreach(image => graphics.drawImage(image, StartX, StartY, getWidth, getHeight, this))

object MultiLayeredTile:
  val StartX = 0
  val StartY = 0
  val BackgroundImage: Image = ImageIcon(ImageManager.BackGround.path).getImage
