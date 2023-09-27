package serialization

import controller.game.GameController
import model.room.*
import model.cells.Position
import io.circe.parser.*
import io.circe.{Decoder, HCursor, Json}
import io.circe.syntax.*
import model.cells.properties.Item
import model.game.CurrentGame
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.gameMap.*
import serialization.JsonDecoder.saveGameDecoder
import serialization.JsonEncoder.saveGameEncoder
import java.awt.GraphicsEnvironment
import java.awt.event.KeyEvent
import java.nio.file.{Files, Paths}

class SaveGameEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:
  val path: String = PathSave
  var position: Position = _
  var startPosition: Position = _
  var map: GameMap = _
  var originalMap: GameMap = _
  var room: Room = _
  var score: Int = _
  var items: List[Item] = _
  var minMap: List[MinimapElement] = _

  override def beforeEach(): Unit =
    super.beforeEach()
    if !GraphicsEnvironment.isHeadless then
      GameController.startGame(JsonTestFile)
      for _ <- 0 to RoomImpl.DefaultHeight do GameController.movePlayer(KeyEvent.VK_S)
      GameController.movePlayer(KeyEvent.VK_D)
      for _ <- 0 to RoomImpl.DefaultHeight do GameController.movePlayer(KeyEvent.VK_W)
      position = CurrentGame.currentPosition
      originalMap = CurrentGame.originalGameMap
      room = CurrentGame.currentRoom
      map = CurrentGame.gameMap
      startPosition = CurrentGame.startPositionInRoom
      score = CurrentGame.scoreCounter
      items = CurrentGame.itemHolder.itemOwned
      minMap = CurrentGame.minimapElement

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then GameController.view.dispose()

  "A game save" should "be encodable and decodable " in {
    if !GraphicsEnvironment.isHeadless then
      saveGameEncoder.apply(CurrentGame) shouldBe a[Json]
      val saveJ: Json = saveGameEncoder.apply(CurrentGame)
      val (
        originalMap2,
        currentMap2,
        currentRoom2,
        currentPlayerPosition2,
        startPlayerPosition2,
        itemList2,
        score2,
        minmap2
      ) =
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
      minmap2 should be(minMap)
      currentPlayerPosition2 should be(position)
  }

  "A game" should "be savable" in {
    if !GraphicsEnvironment.isHeadless then
      GameController.saveGame()
      assert(Files.exists(Paths.get(path)), s"File does not exist.")
  }

  "A game" should "be retrievable from the save file" in {
    if !GraphicsEnvironment.isHeadless then
      GameController.saveGame()
      val json: Json = JsonDecoder
        .getJsonFromPath(path)
        .toOption
        .get
      CurrentGame.load(json)
      isEqual(
        CurrentGame.originalGameMap,
        originalMap
      ) should be(true)
      isEqual(CurrentGame.gameMap, map) should be(true)
      isEqual(CurrentGame.currentRoom, room) should be(true)
      CurrentGame.startPositionInRoom should be(startPosition)
      CurrentGame.scoreCounter should be(score)
      CurrentGame.itemHolder.itemOwned should be(items)
      CurrentGame.currentPosition should be(position)
  }
