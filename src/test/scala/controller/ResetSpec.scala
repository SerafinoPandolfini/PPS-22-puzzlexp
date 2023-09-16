package controller

import controller.game.GameController
import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.cells.logic.CellExtension.*
import model.cells.*
import model.cells.properties.Item
import model.game.CurrentGame
import model.gameMap.*
import model.room.{Room, RoomBuilder, RoomImpl, RoomLink}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.TryValues.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import serialization.JsonDecoder
import utils.TestUtils.*

import java.awt.GraphicsEnvironment
import java.awt.event.KeyEvent

class ResetSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var item: Item = _
  var item2: Item = _
  var item3: Item = _

  override def beforeEach(): Unit =
    super.beforeEach()
    if !GraphicsEnvironment.isHeadless then
      GameController.startGame("src/main/resources/json/testMap.json")
      GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_S)
      item = CurrentGame.currentRoom.getCell(2, 3).get.cellItem // box
      GameController.movePlayer(KeyEvent.VK_D)
      item2 = CurrentGame.currentRoom.getCell(2, 3).get.cellItem // empty
      item3 = CurrentGame.currentRoom.getCell(3, 3).get.cellItem // box

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then GameController.view.dispose()

  "A reset" should "be done when the player change room" in {
    if !GraphicsEnvironment.isHeadless then
      item should not be item2
      item should be(item3)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_D)
      val item4 = CurrentGame.currentRoom.getCell(2, 3).get.cellItem
      item4 should not be item2
      item4 should be(item)
      val item5 = CurrentGame.currentRoom.getCell(3, 3).get.cellItem
      item5 should not be item
  }

  "A reset" should "be done when the player press R" in {
    if !GraphicsEnvironment.isHeadless then
      item should not be item2
      item should be(item3)
      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_R).actionPerformed(null)
      val item4 = CurrentGame.currentRoom.getCell(2, 3).get.cellItem
      item4 should not be item2
      item4 should be(item)
      val item5 = CurrentGame.currentRoom.getCell(3, 3).get.cellItem
      item5 should not be item
  }

  "A reset" should "be done when the player dies" in {
    if !GraphicsEnvironment.isHeadless then
      item should not be item2
      item should be(item3)
      for _ <- 0 to RoomImpl.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      for _ <- 0 to RoomImpl.DefaultWidth do GameController.movePlayer(KeyEvent.VK_D)
      val item4 = CurrentGame.currentRoom.getCell(2, 3).get.cellItem
      item4 should not be item2
      item4 should be(item)
      val item5 = CurrentGame.currentRoom.getCell(3, 3).get.cellItem
      item5 should not be item
  }
