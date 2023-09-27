package controller

import controller.game.GameController
import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.cells.{BasicCell, LockCell, Position, RockCell, WallCell}
import model.room.{Room, RoomBuilder, RoomImpl, RoomLink}
import model.gameMap.*
import model.game.CurrentGame
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.TryValues.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.logic.CellExtension.*
import model.cells.properties.{Direction, Item}
import serialization.JsonDecoder

import java.awt.GraphicsEnvironment
import java.awt.event.KeyEvent

class ControllerSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:

  override def beforeEach(): Unit =
    super.beforeEach()
    if !GraphicsEnvironment.isHeadless then GameController.startGame(JsonTestFile)

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
      Given("a number of key in the map and an item holder without keys")
      val num = CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Key)
      CurrentGame.itemHolder.itemOwned.contains(Item.Key) should be(false)
      When("the player moves on a key")
      for _ <- 0 to RoomImpl.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_D)
      Then("the number of keys in the room should decrease by 1")
      CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Key) should be(num - 1)
      And("The item holder should contain a key")
      CurrentGame.itemHolder.itemOwned.contains(Item.Key) should be(true)
  }

  "A controller" should "be able to reset the room" in {
    if !GraphicsEnvironment.isHeadless then
      Given("the initial position and state of the elements")
      val boxBefore = CurrentGame.currentRoom.getCell(Position2_3).get
      val keyBefore = CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Key)
      val pickBefore = CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Pick)
      val rockBefore = CurrentGame.currentRoom.cells.collect { case c: RockCell => c }.count(c => c.broken)
      val lockBefore = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.count(c => c.open)
      val itemBefore = CurrentGame.itemHolder
      When("a player moves and interact with them")
      for _ <- 0 to RoomImpl.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_D)
      for _ <- 0 to RoomImpl.DefaultHeight do GameController.movePlayer(KeyEvent.VK_W)
      val boxAfter = CurrentGame.currentRoom.getCell(Position2_3).get
      val keyAfter = CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Key)
      val pickAfter = CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Pick)
      val rockAfter = CurrentGame.currentRoom.cells.collect { case c: RockCell => c }.count(c => c.broken)
      val lockAfter = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.count(c => c.open)
      val itemAfter = CurrentGame.itemHolder
      Then("the box position should change")
      boxBefore should not be boxAfter
      And("the key should be piked up")
      keyAfter should not be keyBefore
      And("the pick should be piked up")
      pickAfter should not be pickBefore
      And("The rock should be broken")
      rockAfter should not be rockBefore
      And("The lock should be opened")
      lockAfter should not be lockBefore
      And("The item holder should be updated")
      itemAfter should not be itemBefore
      When("the game is resetted")
      GameController.resetRoom()
      val boxReset = CurrentGame.currentRoom.getCell(Position2_3).get
      val keyReset = CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Key)
      val pickReset = CurrentGame.currentRoom.cells.count(c => c.cellItem == Item.Pick)
      val rockReset = CurrentGame.currentRoom.cells.collect { case c: RockCell => c }.count(c => c.broken)
      val lockReset = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.count(c => c.open)
      val itemReset = CurrentGame.itemHolder
      Then("The box should get back in the original position")
      boxBefore should be(boxReset)
      And("the key should still be picked up")
      keyReset should be(keyAfter)
      And("The item holder should not change")
      itemReset should be(itemAfter)
      And("The pick should be picked up")
      pickReset should be(pickAfter)
      And("the rock should not be broken")
      rockReset should be(rockBefore)
      And("the lock should stay opened")
      lockReset should be(lockAfter)
  }
