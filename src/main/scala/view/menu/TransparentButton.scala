package view.menu

import utils.constants.ColorManager
import utils.constants.GraphicManager.Origin
import view.menu.TransparentButton

import java.awt.*
import java.awt.geom.RoundRectangle2D
import javax.swing.JButton

/** This class creates a custom [[JButton]] with border radius, a black transparent background and a white font */
class TransparentButton(text: String) extends JButton(text):

  /** @param g
    *   the graphic object Calls the UI delegate's paint method
    */
  override protected def paintComponent(g: Graphics): Unit =
    val g2d = g.asInstanceOf[Graphics2D]
    val shape: Shape =
      RoundRectangle2D.Double(
        Origin.x,
        Origin.y,
        TransparentButton.ButtonWidth - 1,
        TransparentButton.ButtonHeight - 1,
        TransparentButton.BorderRadius,
        TransparentButton.BorderRadius
      )
    g2d.setColor(ColorManager.TransparentButtons)
    g2d.fill(shape)
    g2d.draw(shape)
    super.paintComponent(g)

  setPreferredSize(Dimension(TransparentButton.ButtonWidth, TransparentButton.ButtonHeight))
  setForeground(Color.WHITE)
  setBackground(Color.BLACK)
  setFocusPainted(false)
  setContentAreaFilled(false)
  setBorderPainted(false)

object TransparentButton:
  val ButtonWidth: Int = 150
  val ButtonHeight: Int = 40
  val BorderRadius: Int = 20
