package view

import controller.game.GameController
import model.cells.properties.Direction
import model.room.{Room, RoomBuilder}
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import serialization.JsonDecoder
import utils.TestUtils.*
import utils.constants.ImageManager

import java.awt.event.KeyEvent
import scala.None
import java.awt.GraphicsEnvironment

class GUISpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:

  override def beforeEach(): Unit =
    super.beforeEach()
    if !GraphicsEnvironment.isHeadless then GameController.startGame(JsonTestFile)

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then GameController.view.dispose()

  "Each event of key pressing" should "move the character" in {
    if !GraphicsEnvironment.isHeadless then
      Given("a game GUI and a player")
      val characterTile = GameController.view.tiles.values.find(t => t.playerImage.isDefined).get
      val expectedTileAfterD = GameController.view.tiles(Position2_1)
      val expectedTileAfterS = GameController.view.tiles(Position2_2)
      val expectedTileAfterA = GameController.view.tiles(Position1_2)
      val expectedTileAfterW = GameController.view.tiles(Position1_1)

      When("the player press D")
      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_D).actionPerformed(null)
      Then("The player image should disappear from the current tile")
      characterTile.playerImage.isDefined should be(false)
      And("appear in the tile on the right of the previous tile")
      expectedTileAfterD.playerImage.isDefined should be(true)

      When("the player press S")
      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_S).actionPerformed(null)
      Then("The player image should disappear from the current tile")
      expectedTileAfterD.playerImage.isDefined should be(false)
      And("appear in the tile below of the previous tile")
      expectedTileAfterS.playerImage.isDefined should be(true)

      When("the player press A")
      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_A).actionPerformed(null)
      Then("The player image should disappear from the current tile")
      expectedTileAfterS.playerImage.isDefined should be(false)
      And("appear in the tile on the left of the previous tile")
      expectedTileAfterA.playerImage.isDefined should be(true)

      When("the player press W")
      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_W).actionPerformed(null)
      Then("The player image should disappear from the current tile")
      expectedTileAfterA.playerImage.isDefined should be(false)
      And("appear in the tile above of the previous tile")
      expectedTileAfterW.playerImage.isDefined should be(true)

  }
