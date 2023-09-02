package utils

import model.cells.Direction
import java.awt.event.KeyEvent

object KeyDirectionMapping:
  given Conversion[Int, Direction] = _ match
    case KeyEvent.VK_W => Direction.Up
    case KeyEvent.VK_A => Direction.Left
    case KeyEvent.VK_S => Direction.Down
    case KeyEvent.VK_D => Direction.Right

  given Conversion[Int, String] = _ match
    case KeyEvent.VK_S => ImageManager.CharacterDown.path
    case KeyEvent.VK_W => ImageManager.CharacterUp.path
    case KeyEvent.VK_A => ImageManager.CharacterLeft.path
    case KeyEvent.VK_D => ImageManager.CharacterRight.path
