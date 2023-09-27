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

  var item2_3Before: Item = _
  var item2_3After: Item = _
  var item3_3after: Item = _

  override def beforeEach(): Unit =
    super.beforeEach()
    if !GraphicsEnvironment.isHeadless then
      GameController.startGame(JsonTestFile)
      GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_S)
      item2_3Before = CurrentGame.currentRoom.getCell(Position2_3).get.cellItem
      GameController.movePlayer(KeyEvent.VK_D)
      item2_3After = CurrentGame.currentRoom.getCell(Position2_3).get.cellItem
      item3_3after = CurrentGame.currentRoom.getCell(Position3_3).get.cellItem

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then GameController.view.dispose()

  "A reset" should "be done when the player change room" in {
    if !GraphicsEnvironment.isHeadless then
      item2_3Before should not be item2_3After
      item2_3Before should be(item3_3after)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_D)
      val item2_3 = CurrentGame.currentRoom.getCell(Position2_3).get.cellItem
      item2_3 should not be item2_3After
      item2_3 should be(item2_3Before)
      val item3_3 = CurrentGame.currentRoom.getCell(Position3_3).get.cellItem
      item3_3 should not be item3_3after
  }

  "A reset" should "be done when the player press R" in {
    if !GraphicsEnvironment.isHeadless then
      item2_3Before should not be item2_3After
      item2_3Before should be(item3_3after)
      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_R).actionPerformed(null)
      val item2_3 = CurrentGame.currentRoom.getCell(Position2_3).get.cellItem
      item2_3 should not be item2_3After
      item2_3 should be(item2_3Before)
      val item3_3 = CurrentGame.currentRoom.getCell(Position3_3).get.cellItem
      item3_3 should not be item3_3after
  }

  "A reset" should "be done when the player dies" in {
    if !GraphicsEnvironment.isHeadless then
      item2_3Before should not be item2_3After
      item2_3Before should be(item3_3after)
      for _ <- 0 to RoomImpl.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      for _ <- 0 to RoomImpl.DefaultWidth do GameController.movePlayer(KeyEvent.VK_D)
      val item2_3 = CurrentGame.currentRoom.getCell(Position2_3).get.cellItem
      item2_3 should not be item2_3After
      item2_3 should be(item2_3Before)
      val item3_3 = CurrentGame.currentRoom.getCell(Position3_3).get.cellItem
      item3_3 should not be item3_3after
  }
