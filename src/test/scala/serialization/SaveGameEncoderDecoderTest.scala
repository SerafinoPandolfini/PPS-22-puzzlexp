package serialization

import controller.GameController
import model.room.*
import model.cells.{Item, Position}
import utils.TestUtils.*
import io.circe.parser.*
import io.circe.{Decoder, HCursor, Json}
import io.circe.syntax.*
import model.game.CurrentGame
import serialization.JsonDecoder.{saveGameDecoder, getAbsolutePath, getJsonFromPath}
import serialization.JsonEncoder.saveGameEncoder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.gameMap.*

import java.awt.GraphicsEnvironment
import java.awt.event.KeyEvent
import java.nio.file.{Files, Paths}

class SaveGameEncoderDecoderTest extends AnyFlatSpec with BeforeAndAfterEach:

  var position: Position = _
  var startPosition: Position = _
  var map: GameMap = _
  var originalMap: GameMap = _
  var room: Room = _
  var score: Int = _
  var items: List[Item] = _

  override def beforeEach(): Unit =
    super.beforeEach()
    if !GraphicsEnvironment.isHeadless then
      GameController.startGame("src/main/resources/json/testMap.json")
      for _ <- 0 to Room.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_D)
      for _ <- 0 to Room.DefaultHeight do GameController.movePlayer(KeyEvent.VK_W)
      position = CurrentGame.currentPosition
      originalMap = CurrentGame.originalGameMap
      room = CurrentGame.currentRoom
      map = CurrentGame.gameMap
      startPosition = CurrentGame.startPositionInRoom
      score = CurrentGame.scoreCounter
      items = CurrentGame.itemHolder.itemOwned

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then GameController.view.dispose()

  "A game save" should "be encodable and decodable in " in {
    if !GraphicsEnvironment.isHeadless then
      saveGameEncoder.apply(CurrentGame) shouldBe a[Json]
      val saveJ: Json = saveGameEncoder.apply(CurrentGame)
      val (originalMap2, currentMap2, currentRoom2, currentPlayerPosition2, startPlayerPosition2, itemList2, score2) =
        saveGameDecoder.apply(saveJ.hcursor).toOption.get
      isEqual(
        JsonDecoder.mapDecoder(JsonDecoder.getJsonFromPath(originalMap2).toOption.get.hcursor).toOption.get,
        originalMap
      ) should be(true)
      isEqual(currentMap2, map) should be(true)
      isEqual(currentRoom2, room) should be(true)
      startPlayerPosition2 should be(startPosition)
      score2 should be(score)
      itemList2 should be(items)
      currentPlayerPosition2 should be(position)
  }
