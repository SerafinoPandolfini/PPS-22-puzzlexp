package view

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

  var game: Game = _
  val timeSleep: Int = 1000

  override def beforeEach(): Unit =
    super.beforeEach()
    game = new Game

  "Each event of key pressing" should "be associated to the right position of the character" in {
    game.cells.head.placeCharacter(ImageManager.CharacterLeft.path)
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
    println(game.cells.find(t => t.isCharacterHere).get)
    game.cells.indexOf(game.cells.find(t => t.isCharacterHere).get) should be(26)
    robot.keyPress(KeyEvent.VK_D)
    robot.keyRelease(KeyEvent.VK_D)
    Thread.sleep(timeSleep)
    println(game.cells.find(t => t.isCharacterHere).get)
    game.cells.indexOf(game.cells.find(t => t.isCharacterHere).get) should be(27)
    robot.keyPress(KeyEvent.VK_W)
    robot.keyRelease(KeyEvent.VK_W)
    Thread.sleep(timeSleep)
    println(game.cells.find(t => t.isCharacterHere).get)
    game.cells.indexOf(game.cells.find(t => t.isCharacterHere).get) should be(2)
    robot.keyPress(KeyEvent.VK_A)
    robot.keyRelease(KeyEvent.VK_A)
    Thread.sleep(timeSleep)
    println(game.cells.find(t => t.isCharacterHere).get)
    game.cells.indexOf(game.cells.find(t => t.isCharacterHere).get) should be(1)
    robot.keyPress(KeyEvent.VK_S)
    robot.keyRelease(KeyEvent.VK_S)
    Thread.sleep(timeSleep)
    println(game.cells.find(t => t.isCharacterHere).get)
    game.cells.indexOf(game.cells.find(t => t.isCharacterHere).get) should be(26)

  }
