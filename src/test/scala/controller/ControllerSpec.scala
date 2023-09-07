package controller

import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.cells.{BasicCell, Direction, LockCell, Item, Position, RockCell, WallCell}
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
    if !GraphicsEnvironment.isHeadless then GameController.startGame("src/main/resources/json/testMap.json")

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then GameController.view.dispose()

  "A controller" should "let the player move and change room" in {
    if !GraphicsEnvironment.isHeadless then
      val room1 = CurrentGame.currentRoom
      GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_A)
      GameController.movePlayer(KeyEvent.VK_A)
      CurrentGame.currentRoom should not be room1
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

  "A controller" should "be able to reset the room" in {
    if !GraphicsEnvironment.isHeadless then
      val boxBefore = CurrentGame.currentRoom.getCell(2, 3).get
      val keyBefore = CurrentGame.currentRoom.cells.exists(c => c.cellItem == Item.Key)
      val pickBefore = CurrentGame.currentRoom.cells.exists(c => c.cellItem == Item.Pick)
      val rockBefore = CurrentGame.currentRoom.cells.collect { case c: RockCell => c }.head.broken
      val lockBefore = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.head.open
      val itemBefore = CurrentGame.itemHolder
      for _ <- 0 to Room.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_D)
      for _ <- 0 to Room.DefaultHeight do GameController.movePlayer(KeyEvent.VK_W)
      val boxAfter = CurrentGame.currentRoom.getCell(2, 3).get
      val keyAfter = CurrentGame.currentRoom.cells.exists(c => c.cellItem == Item.Key)
      val pickAfter = CurrentGame.currentRoom.cells.exists(c => c.cellItem == Item.Pick)
      val rockAfter = CurrentGame.currentRoom.cells.collect { case c: RockCell => c }.head.broken
      val lockAfter = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.head.open
      val itemAfter = CurrentGame.itemHolder
      boxBefore should not be boxAfter
      keyAfter should not be keyBefore
      itemAfter should not be itemBefore
      pickAfter should not be pickBefore
      rockAfter should not be rockBefore
      lockAfter should not be lockBefore
      GameController.resetRoom()
      val boxReset = CurrentGame.currentRoom.getCell(2, 3).get
      val keyReset = CurrentGame.currentRoom.cells.exists(c => c.cellItem == Item.Key)
      val pickReset = CurrentGame.currentRoom.cells.exists(c => c.cellItem == Item.Pick)
      val rockReset = CurrentGame.currentRoom.cells.collect { case c: RockCell => c }.head.broken
      val lockReset = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.head.open
      val itemReset = CurrentGame.itemHolder
      boxBefore should be(boxReset)
      keyReset should be(keyAfter)
      itemReset should be(itemAfter)
      pickReset should be(pickAfter)
      rockReset should be(rockBefore)
      lockReset should be(lockAfter)
  }
