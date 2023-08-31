package view

import controller.GameController
import model.room.{Room, RoomBuilder}
import model.cells.Direction
import utils.ImageManager
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*

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
    game = new GameView(room, position1_1)
    GameController.startGame("src/main/resources/json/testMap.json")

  "Each event of key pressing" should "move the character" in {

    val characterTile = game.tiles.find(t => t._2.playerImage.isDefined).get
    val expectedTileAfterD = game.tiles(position2_1)
    val expectedTileAfterS = game.tiles(position2_2)
    val expectedTileAfterA = game.tiles(position1_2)
    val expectedTileAfterW = game.tiles(position1_1)

    // Remove character from the initial tile and place it on the target tile

    // Simulate key events using the KeyHandler
    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_D).actionPerformed(null)
    characterTile._2.playerImage.isDefined should be(false)
    expectedTileAfterD.playerImage.isDefined should be(true)

    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_S).actionPerformed(null)
    expectedTileAfterA.playerImage.isDefined should be(false)
    expectedTileAfterS.playerImage.isDefined should be(true)

    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_A).actionPerformed(null)
    expectedTileAfterW.playerImage.isDefined should be(false)
    expectedTileAfterA.playerImage.isDefined should be(true)

    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_W).actionPerformed(null)
    expectedTileAfterD.playerImage.isDefined should be(false)
    expectedTileAfterW.playerImage.isDefined should be(true)

  }
