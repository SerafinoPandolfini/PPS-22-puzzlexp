package controller

import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.cells.{BasicCell, Direction, Item, Position, WallCell}
import model.room.{Room, RoomBuilder, RoomLink}
import model.gameMap.*
import model.game.CurrentGame
import org.scalatest.BeforeAndAfterEach
import org.scalatest.TryValues.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.logic.CellExtension.*
import java.awt.GraphicsEnvironment
import java.awt.event.KeyEvent

class ControllerSpec extends AnyFlatSpec with BeforeAndAfterEach:

  override def beforeEach(): Unit =
    super.beforeEach()
    GameController.startGame("src/main/resources/json/testMap.json")

  "A controller" should "let the player move and change room" in {
    if !GraphicsEnvironment.isHeadless then
      val room1 = CurrentGame.currentRoom
      GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_A)
      CurrentGame.currentRoom should not be room1
  }

  "A controller" should "reset the room" in {
    if !GraphicsEnvironment.isHeadless then

      val it = CurrentGame.currentRoom.cells.find(c => c.position == (2, 3)).get.cellItem
      val set2 = CurrentGame.currentRoom.cells
        .find(c => c.position == (2, 3))
        .get
        .asInstanceOf[BasicCell]
        .updateItem(CurrentGame.currentRoom.cells, Item.Empty, Direction.Down)
      CurrentGame.currentRoom.updateCells(set2)
      val it2 = CurrentGame.currentRoom.cells.find(c => c.position == (2, 3)).get.cellItem
      it should not be it2
      GameController.resetRoom()
      val it3 = CurrentGame.currentRoom.cells.find(c => c.position == (2, 3)).get.cellItem
      it should be(it3)
  }

  "A controller" should "reset the room when the player change room" in {
    if !GraphicsEnvironment.isHeadless then
      GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_S)
      val item = CurrentGame.currentRoom.getCell(2, 3).get.cellItem // box
      GameController.movePlayer(KeyEvent.VK_D)
      val item2 = CurrentGame.currentRoom.getCell(2, 3).get.cellItem // empty
      item should not be item2
      val item3 = CurrentGame.currentRoom.getCell(3, 3).get.cellItem // box
      item should be(item3)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_D)
      val item4 = CurrentGame.currentRoom.getCell(2, 3).get.cellItem // box
      item4 should not be item2
      item4 should be(item)
      val item5 = CurrentGame.currentRoom.getCell(3, 3).get.cellItem // empty
      item5 should not be item
  }

  "A controller" should "let the player pick up items" in {
    if !GraphicsEnvironment.isHeadless then
      CurrentGame.currentRoom.cells.find(c => c.cellItem == Item.Key).get.position
      CurrentGame.itemHolder.itemOwned.contains(Item.Key) should be(false)
      for _ <- 0 to Room.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_D)
      CurrentGame.currentRoom.cells.exists(c => c.cellItem == Item.Key) should be(false)
      CurrentGame.itemHolder.itemOwned.contains(Item.Key) should be(true)
  }
