package view

import model.room.{Room, RoomBuilder}
import model.cells.Direction
import utils.ImageManager
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

import java.awt.event.KeyEvent
import javax.swing.JPanel
import scala.None
import java.awt.Robot

class GUITest extends AnyFlatSpec with BeforeAndAfterEach:

  var game: GameView = _
  val timeSleep: Int = 1000
  var room: Room = _

  override def beforeEach(): Unit =
    super.beforeEach()
    room = new RoomBuilder().build
    game = new GameView(room, (0, 0))

  "Each event of key pressing" should "be associated to the right position of the character" in {
    game.tiles.head.placeCharacter(ImageManager.CharacterLeft.path)
    game.keyHandler.keyAction(KeyEvent.VK_D) should be(
      Some(KeyAction(ImageManager.CharacterRight.path))
    )
    game.keyHandler.keyAction(KeyEvent.VK_A) should be(
      Some(KeyAction(ImageManager.CharacterLeft.path))
    )
    game.keyHandler.keyAction(KeyEvent.VK_S) should be(
      Some(KeyAction(ImageManager.CharacterDown.path))
    )
    game.keyHandler.keyAction(KeyEvent.VK_W) should be(
      Some(KeyAction(ImageManager.CharacterUp.path))
    )
  }

  "Each event of key pressing" should "move the charachter" in {
    val robot = new Robot()
    game.tiles.find(t => t.isCharacterHere).get.unplaceCharacter()
    game.tiles(26).placeCharacter(ImageManager.CharacterDown.path)
    game.tiles.indexOf(game.tiles.find(t => t.isCharacterHere).get) should be(26)
    robot.keyPress(KeyEvent.VK_D)
    robot.keyRelease(KeyEvent.VK_D)
    Thread.sleep(timeSleep)
    println(game.tiles.find(t => t.isCharacterHere).get)
    game.tiles.indexOf(game.tiles.find(t => t.isCharacterHere).get) should be(27)
    robot.keyPress(KeyEvent.VK_W)
    robot.keyRelease(KeyEvent.VK_W)
    Thread.sleep(timeSleep)
    println(game.tiles.find(t => t.isCharacterHere).get)
    game.tiles.indexOf(game.tiles.find(t => t.isCharacterHere).get) should be(2)
    robot.keyPress(KeyEvent.VK_A)
    robot.keyRelease(KeyEvent.VK_A)
    Thread.sleep(timeSleep)
    println(game.tiles.find(t => t.isCharacterHere).get)
    game.tiles.indexOf(game.tiles.find(t => t.isCharacterHere).get) should be(1)
    robot.keyPress(KeyEvent.VK_S)
    robot.keyRelease(KeyEvent.VK_S)
    Thread.sleep(timeSleep)
    println(game.tiles.find(t => t.isCharacterHere).get)
    game.tiles.indexOf(game.tiles.find(t => t.isCharacterHere).get) should be(26)

  }
