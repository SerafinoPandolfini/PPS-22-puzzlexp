package controller

import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.cells.{BasicCell, Direction, Item, Position, WallCell}
import model.room.{Room, RoomBuilder, RoomLink}
import model.gameMap.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.TryValues.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*

import java.awt.event.KeyEvent

class ControllerSpec extends AnyFlatSpec with BeforeAndAfterEach:

  override def beforeEach(): Unit =
    super.beforeEach()
    GameController.startGame("src/main/resources/json/testMap.json")

  "A controller" should "let the player move and change room" in {
    val room1 = GameController.currentGame.currentRoom
    GameController.movePlayer(KeyEvent.VK_S)
    println(GameController.currentGame.currentRoom)
    println(GameController.currentGame.currentPosition)
    GameController.movePlayer(KeyEvent.VK_S)
    println(GameController.currentGame.currentRoom)
    println(GameController.currentGame.currentPosition)
    GameController.movePlayer(KeyEvent.VK_A)
    println(GameController.currentGame.currentRoom)
    println(GameController.currentGame.currentPosition)
    GameController.movePlayer(KeyEvent.VK_A)
    println(GameController.currentGame.currentRoom)
    println(GameController.currentGame.currentPosition)
    GameController.currentGame.currentRoom should not be room1
  }

  "A controller" should "reset the room" in {
    val pos = GameController.currentGame.currentRoom.cells.find(c => c.cellItem == Item.Box).get.position
    GameController.movePlayer(KeyEvent.VK_S)
    GameController.movePlayer(KeyEvent.VK_S)
    GameController.movePlayer(KeyEvent.VK_D)
    val pos2 = GameController.currentGame.currentRoom.cells.find(c => c.cellItem == Item.Box).get.position
    pos should not be pos2
    GameController.resetRoom()
    val pos3 = GameController.currentGame.currentRoom.cells.find(c => c.cellItem == Item.Box).get.position
    pos should be(pos3)
  }

  "A controller" should "reset the room when the player change room" in {
    val pos = GameController.currentGame.currentRoom.cells.find(c => c.cellItem == Item.Box).get.position
    GameController.movePlayer(KeyEvent.VK_S)
    GameController.movePlayer(KeyEvent.VK_S)
    GameController.movePlayer(KeyEvent.VK_D)
    val pos2 = GameController.currentGame.currentRoom.cells.find(c => c.cellItem == Item.Box).get.position
    pos should not be pos2
    GameController.movePlayer(KeyEvent.VK_A)
    GameController.movePlayer(KeyEvent.VK_A)
    GameController.movePlayer(KeyEvent.VK_D)
    val pos3 = GameController.currentGame.currentRoom.cells.find(c => c.cellItem == Item.Box).get.position
    pos2 should not be pos3
    pos should be(pos3)
  }
