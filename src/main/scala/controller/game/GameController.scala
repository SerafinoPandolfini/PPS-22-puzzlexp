package controller.game

import controller.game.GameController
import io.circe.Json
import model.cells.*
import model.cells.properties.{Direction, Item}
import model.cells.logic.CellExtension.*
import model.cells.logic.UseItemExtension.usePowerUp
import model.game.{CurrentGame, ItemHolder}
import model.gameMap.{GameMap, MinimapElement}
import model.room.Room
import serialization.{JsonDecoder, JsonEncoder}
import utils.givens.KeyDirectionMapping.given
import utils.PathExtractor.extractPath
import utils.extensions.PositionExtension.+
import view.game.GameView
import view.game.ViewUpdater.*
import model.gameMap.Minimap.createMinimap
import java.awt.event.KeyEvent
import java.io.PrintWriter
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
    val appDir = Paths.get(System.getProperty("user.home"), "puzzlexp", "saves")
    JsonDecoder.getJsonFromPath(path) match
      case Success(jsonData) =>
        if Path.of(path).startsWith(appDir) then CurrentGame.load(jsonData)
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
    val appDir = Paths.get(System.getProperty("user.home"), "puzzlexp", "saves")
    if !Files.exists(appDir) then Files.createDirectories(appDir)
    val outputFile = java.io.File(
      appDir.toString + java.io.File.separator + CurrentGame.originalGameMap.name + ".json"
    )
    val printWriter = PrintWriter(outputFile)
    printWriter.write(JsonEncoder.saveGameEncoder.apply(CurrentGame).spaces2)
    printWriter.close()

  /** pause the game
    */
  def pauseGame(): Unit = _view.pause(CurrentGame.minimapElement)

  /** goes back to game from the pause screen
    */
  def backToGame(): Unit = _view.back()

  /** delete the current frame
    */
  def endGame(): Unit = _view.dispose()

  /** get the room name
    * @return
    *   the current room name
    */
  def getCurrentRoomName: String = CurrentGame.currentRoom.name

object simulate extends App:
  GameController.startGame("src/main/resources/json/FirstMap.json")

object useSave extends App:
  val appDir = Paths.get(System.getProperty("user.home"), "puzzlexp", "saves", "FirstMap.json").toString
  GameController.startGame(appDir)
