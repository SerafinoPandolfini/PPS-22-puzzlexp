package utils

import ConstantUtils.{BorderRadius, ButtonHeight, ButtonWidth, Origin}
import ColorManager.TransparentButtons
import java.awt.{Color, Dimension, Graphics, Graphics2D, Shape}
import java.awt.geom.RoundRectangle2D
import javax.swing.JButton

/** This class creates a custom [[JButton]] with border radius, a black transparent background and a white font */
class TransparentButton(text: String) extends JButton(text):

  override protected def paintComponent(g: Graphics): Unit =
    val g2d = g.asInstanceOf[Graphics2D]
    val shape: Shape =
      RoundRectangle2D.Double(Origin.x, Origin.y, ButtonWidth - 1, ButtonHeight - 1, BorderRadius, BorderRadius)
    g2d.setColor(ColorManager.TransparentButtons.color)
    g2d.fill(shape)
    g2d.draw(shape)
    super.paintComponent(g)

  setPreferredSize(Dimension(ButtonWidth, ButtonHeight))
  setForeground(Color.WHITE)
  setBackground(Color.BLACK)
  setFocusPainted(false)
  setContentAreaFilled(false)
  setBorderPainted(false)
