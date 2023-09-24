package utils.givens

import model.cells.properties.Direction
import utils.constants.ImageManager

import java.awt.BorderLayout
import java.net.URL
import javax.swing.JLabel

object DirectionMapping:
  given Conversion[Direction, (URL, String)] = _ match
    case Direction.Right => (ImageManager.LinkHorizontal.path, BorderLayout.LINE_END)
    case Direction.Left  => (ImageManager.LinkHorizontal.path, BorderLayout.LINE_START)
    case Direction.Up    => (ImageManager.LinkVertical.path, BorderLayout.PAGE_START)
    case Direction.Down  => (ImageManager.LinkVertical.path, BorderLayout.PAGE_END)
