package utils.givens

import model.cells.properties.Direction
import utils.constants.ImageManager

import java.awt.event.KeyEvent
import java.net.URL

object KeyDirectionMapping:

  /** coverts the [[KeyEvent]] into a [[Direction]]
    * @return
    *   the direction in which the KeyEvent is bound
    */
  given Conversion[Int, Direction] = _ match
    case KeyEvent.VK_W => Direction.Up
    case KeyEvent.VK_A => Direction.Left
    case KeyEvent.VK_S => Direction.Down
    case KeyEvent.VK_D => Direction.Right

  /** coverts the [[KeyEvent]] into an [[URL]] to an image
    * @return
    *   the [[URL]] to the image of the player turned in the corresponding direction
    */
  given Conversion[Int, URL] = _ match
    case KeyEvent.VK_S => ImageManager.CharacterDown.path
    case KeyEvent.VK_W => ImageManager.CharacterUp.path
    case KeyEvent.VK_A => ImageManager.CharacterLeft.path
    case KeyEvent.VK_D => ImageManager.CharacterRight.path
