package utils.givens

import model.cells.properties.Direction
import utils.constants.ImageManager

import java.awt.BorderLayout
import java.net.URL
import javax.swing.JLabel

object DirectionMapping:
  /** converts a [[Direction]] into a tuple of [[URL]] and [[String]]
    * @return
    *   a tuple of [[URL]] and [[String]] representing the URL of the image to show and the [[BorderLayout]] position in
    *   which palace it
    */
  given Conversion[Direction, (URL, String)] = _ match
    case Direction.Right => (ImageManager.LinkHorizontal.path, BorderLayout.LINE_END)
    case Direction.Left  => (ImageManager.LinkHorizontal.path, BorderLayout.LINE_START)
    case Direction.Up    => (ImageManager.LinkVertical.path, BorderLayout.PAGE_START)
    case Direction.Down  => (ImageManager.LinkVertical.path, BorderLayout.PAGE_END)
