package view

import model.cells.Direction
import utils.ImageManager
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

import java.awt.event.KeyEvent
import javax.swing.JPanel
import scala.None

class GUITest extends AnyFlatSpec with BeforeAndAfterEach:
  var tile: Tile = _
  var keyHandler: KeyHandler = KeyHandler()

  override def beforeEach(): Unit =
    super.beforeEach()
    val cellSize: Int = 32
    val panel: JPanel = JPanel()
    tile = Tile(panel, cellSize)

  "a tile" should "be able to place the character" in {
    tile.isCharacterHere should be(false)
    tile.placeCharacter(ImageManager.CHARACTER_LEFT.path)
    tile.isCharacterHere should be(true)
  }

  "Each event of key pressing" should "be associated to the right position of the character" in {
    tile.placeCharacter(ImageManager.CHARACTER_LEFT.path)
    keyHandler.keyAction(KeyEvent.VK_D) should be(
      Some(KeyAction(KeyEvent.VK_D, ImageManager.CHARACTER_RIGHT.path, Direction.Right))
    )
    keyHandler.keyAction(KeyEvent.VK_A) should be(
      Some(KeyAction(KeyEvent.VK_A, ImageManager.CHARACTER_LEFT.path, Direction.Left))
    )
    keyHandler.keyAction(KeyEvent.VK_S) should be(
      Some(KeyAction(KeyEvent.VK_S, ImageManager.CHARACTER_DOWN.path, Direction.Down))
    )
    keyHandler.keyAction(KeyEvent.VK_W) should be(
      Some(KeyAction(KeyEvent.VK_W, ImageManager.CHARACTER_UP.path, Direction.Up))
    )
  }

  "a tile" should "be able to unplace the character" in {
    tile.placeCharacter(ImageManager.CHARACTER_LEFT.path)
    tile.isCharacterHere should be(true)
    tile.unplaceCharacter()
    tile.isCharacterHere should be(false)
  }
