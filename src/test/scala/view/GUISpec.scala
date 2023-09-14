package view

import controller.GameController
import model.room.{Room, RoomBuilder}
import model.cells.Direction
import utils.ImageManager
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import serialization.JsonDecoder
import utils.TestUtils.*

import java.awt.event.KeyEvent
import scala.None
import java.awt.GraphicsEnvironment

class GUISpec extends AnyFlatSpec with BeforeAndAfterEach:

  override def beforeEach(): Unit =
    super.beforeEach()
    if !GraphicsEnvironment.isHeadless then
      GameController.startGame(JsonDecoder.getAbsolutePath("src/main/resources/json/testMap.json"))

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then GameController.view.dispose()

  "Each event of key pressing" should "move the character" in {
    if !GraphicsEnvironment.isHeadless then
      val characterTile = GameController.view.tiles.find(t => t._2.playerImage.isDefined).get
      val expectedTileAfterD = GameController.view.tiles(position2_1)
      val expectedTileAfterS = GameController.view.tiles(position2_2)
      val expectedTileAfterA = GameController.view.tiles(position1_2)
      val expectedTileAfterW = GameController.view.tiles(position1_1)

      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_D).actionPerformed(null)
      characterTile._2.playerImage.isDefined should be(false)
      expectedTileAfterD.playerImage.isDefined should be(true)

      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_S).actionPerformed(null)
      expectedTileAfterD.playerImage.isDefined should be(false)
      expectedTileAfterS.playerImage.isDefined should be(true)

      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_A).actionPerformed(null)
      expectedTileAfterS.playerImage.isDefined should be(false)
      expectedTileAfterA.playerImage.isDefined should be(true)

      GameController.view.tilesPanel.getActionMap.get("keyAction_" + KeyEvent.VK_W).actionPerformed(null)
      expectedTileAfterA.playerImage.isDefined should be(false)
      expectedTileAfterW.playerImage.isDefined should be(true)

  }
