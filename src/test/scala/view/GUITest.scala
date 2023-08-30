package view

import model.room.{Room, RoomBuilder}
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

  var game: GameView = _
  val timeSleep: Int = 1000
  var room: Room = _

  override def beforeEach(): Unit =
    super.beforeEach()
    room = new RoomBuilder().build
    game = new GameView(room, (0, 0))

  "Each event of key pressing" should "move the character" in {

    val characterTile = game.tiles.find(t => t.isCharacterHere).get
    val targetTile = game.tiles(26)
    val expectedTileAfterD = game.tiles(27)
    val expectedTileAfterW = game.tiles(2)
    val expectedTileAfterA = game.tiles(1)
    val expectedTileAfterS = game.tiles(26)

    // Remove character from the initial tile and place it on the target tile
    characterTile.unplaceCharacter()
    targetTile.placeCharacter(ImageManager.CharacterDown.path)

    // Simulate key events using the KeyHandler
    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_D).actionPerformed(null)
    targetTile.isCharacterHere should be(false)
    expectedTileAfterD.isCharacterHere should be(true)

    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_W).actionPerformed(null)
    expectedTileAfterD.isCharacterHere should be(false)
    expectedTileAfterW.isCharacterHere should be(true)

    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_A).actionPerformed(null)
    expectedTileAfterW.isCharacterHere should be(false)
    expectedTileAfterA.isCharacterHere should be(true)

    game.mainPanel.getActionMap.get("keyAction_" + KeyEvent.VK_S).actionPerformed(null)
    expectedTileAfterA.isCharacterHere should be(false)
    expectedTileAfterS.isCharacterHere should be(true)
  }
