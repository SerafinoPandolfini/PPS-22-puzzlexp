package view

import utils.{ColorManager, ImageManager}

import java.awt.{Color, Dimension, Graphics, Graphics2D, Rectangle}
import javax.swing.{ImageIcon, JButton, JComponent}
import javax.swing.plaf.basic.{BasicArrowButton, BasicScrollBarUI}

/** permits to configure the UI of the scrollBar */
class CustomScrollBarUI() extends BasicScrollBarUI:
  /** configuration of the background (trackColor) and foreground (thumbColor) of the scrollbar */
  override def configureScrollBarColors(): Unit =
    thumbColor = ColorManager.ScrollBarForeground
    trackColor = Color.WHITE

  /** set the foreground of the scrollbar */
  override def paintThumb(g: Graphics, c: JComponent, thumbBounds: Rectangle): Unit =
    if (!thumbBounds.isEmpty && thumbColor != null)
      g.setColor(thumbColor)
      g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height)

  /** set the background of the scrollbar */
  override def paintTrack(g: Graphics, c: JComponent, trackBounds: Rectangle): Unit =
    if (!trackBounds.isEmpty && trackColor != null)
      g.setColor(trackColor)
      g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height)

  /** set the size of the scrollbar */
  override def getPreferredSize(c: JComponent): Dimension =
    val width = CustomScrollBarUI.ScrollBarWidth
    super.getPreferredSize(c)
    Dimension(width, c.getHeight)

  /** configure the decrease button of the scrollbar */
  override def createDecreaseButton(direction: Int): JButton =
    val decreaseButton: JButton = JButton()
    decreaseButton.setPreferredSize(Dimension(CustomScrollBarUI.ZeroDimension, CustomScrollBarUI.ZeroDimension))
    decreaseButton.setBackground(Color.WHITE)
    decreaseButton.setForeground(Color.BLACK)
    decreaseButton

  /** configure the increase button of the scrollbar */
  override def createIncreaseButton(direction: Int): JButton =
    val increaseButton: JButton = JButton()
    increaseButton.setPreferredSize(Dimension(CustomScrollBarUI.ZeroDimension, CustomScrollBarUI.ZeroDimension))
    increaseButton.setBackground(Color.WHITE)
    increaseButton.setForeground(Color.BLACK)
    increaseButton

object CustomScrollBarUI:
  val ScrollBarWidth: Int = 10
  val ZeroDimension: Int = 0
