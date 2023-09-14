package controller

import model.cells.logic.UseItemExtension.usePowerUp
import model.cells.logic.CellExtension.*
import model.cells.*
import model.game.{CurrentGame, ItemHolder}
import model.gameMap.GameMap
import model.room.Room
import serialization.{JsonDecoder, JsonEncoder}
import utils.PositionExtension.+
import utils.KeyDirectionMapping.given
import utils.PathExtractor.extractPath
import view.GameView
import java.io.PrintWriter
import io.circe.Json
import java.awt.event.KeyEvent
import java.nio.file.*
import scala.util.{Failure, Success}

object GameController:
  private[controller] var _view: GameView = _

  def view: GameView = _view

  /** performs the necessary actions to initialize and start the game
    * @param path
    *   the path of the json for the current game
    */
  def startGame(path: String): Unit =
    JsonDecoder.getJsonFromPath(path) match
      case Success(jsonData) =>
        if Path.of(path).startsWith(Paths.get("src/main/resources/saves/").toAbsolutePath) then
          CurrentGame.load(jsonData)
        else
          JsonDecoder.mapDecoder(jsonData.hcursor) match
            case Right(map)  => CurrentGame.initialize(map)
            case Left(error) => println(error)
        _view = GameView(CurrentGame.currentRoom, CurrentGame.currentPosition)
        ToolbarUpdater.updateToolbarLabels()
      case Failure(ex) => println(ex)

  /** Performs the actions needed to move the player
    * @param direction
    *   the [[Direction]] in which the player is moving
    * @return
    *   the [[Position]] in which the player is
    */
  def movePlayer(direction: Int): Unit =
    CurrentGame.currentRoom.playerMove(CurrentGame.currentPosition, direction) match
      case Some(position) =>
        if CurrentGame.currentPosition != position then CurrentGame.checkConsequences(position)
        else
          CurrentGame.currentRoom.updateCells(
            CurrentGame.currentRoom.getCell(position + direction.coordinates).get.usePowerUp()
          )
      case None => PlayerMovement.checkChangeRoom(direction)
    PlayerMovement.checkMoveOnItem()
    view.associateTiles(CurrentGame.currentRoom)
    val playerDirection = CurrentGame.currentRoom.isPlayerDead(CurrentGame.currentPosition) match
      case Success(value) if !value => direction
      case _ =>
        resetRoom()
        KeyEvent.VK_S
    view.updatePlayerImage(CurrentGame.currentPosition, playerDirection)
    ToolbarUpdater.updateToolbarLabels()

  /** Reset the current room
    */
  def resetRoom(): Unit =
    val resettedRoom = CurrentGame.originalGameMap.getRoomFromName(CurrentGame.currentRoom.name).get
    val doors = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.map(c => c.copy(cellItem = Item.Empty))
    resettedRoom.updateCells(doors.asInstanceOf[Set[Cell]])
    val itemEmpty = for
      c <- CurrentGame.currentRoom.cells
      r <- resettedRoom.cells
      if r.cellItem != Item.Box && r.cellItem != Item.Empty && c.position == r.position && c.cellItem != r.cellItem
    yield r.updateItem(resettedRoom.cells, Item.Empty).filter(e => e.position == r.position).head
    resettedRoom.updateCells(itemEmpty)
    CurrentGame.resetRoom(resettedRoom)
    view.associateTiles(CurrentGame.currentRoom)

  /** saves the actual game writing the needed info in a json file
    */
  def saveGame(): Unit =
    val outputFile = java.io.File(s"src/main/resources/saves/${CurrentGame.originalGameMap.name}.json")
    val printWriter = PrintWriter(outputFile)
    printWriter.write(JsonEncoder.saveGameEncoder.apply(CurrentGame).spaces2)
    printWriter.close()

object simulate extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/json/FirstMap.json")
  GameController.startGame(p)

object useSave extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/saves/FirstMap.json")
  GameController.startGame(p)
