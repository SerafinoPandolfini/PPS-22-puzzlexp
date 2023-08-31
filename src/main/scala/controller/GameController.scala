package controller

import model.cells.logic.PowerUpExtension
import model.cells.logic.CellExtension.*
import model.cells.{Cell, Direction, DoorCell, Item, Position}
import model.game.CurrentGame
import model.gameMap.GameMap
import model.room.{Room, RoomBuilder}
import serialization.JsonDecoder
import utils.PositionExtension.+
import utils.KeyDirectionMapping.given
import utils.PathExtractor.extractPath
import view.GameView

import scala.util.{Failure, Success}

object GameController:

  val currentGame: CurrentGame.type = CurrentGame
  private var _view: GameView = _

  def view: GameView = _view

  def startGame(mapPath: String): Unit =
    currentGame.originalGameMap =
      JsonDecoder.mapDecoder(JsonDecoder.getJsonFromPath(mapPath).toOption.get.hcursor).toOption.get
    currentGame.gameMap = JsonDecoder.mapDecoder(JsonDecoder.getJsonFromPath(mapPath).toOption.get.hcursor).toOption.get
    currentGame.currentRoom = currentGame.gameMap.getRoomFromName(currentGame.gameMap.initialRoom).get
    currentGame.startPositionInRoom = currentGame.gameMap.initialPosition
    currentGame.currentPosition = currentGame.gameMap.initialPosition
    _view = GameView(currentGame.currentRoom, currentGame.gameMap.initialPosition)

  /** Performs the actions needed to move the player
    * @param direction
    *   the [[Direction]] in which the player is moving
    * @return
    *   the [[Position]] in which the player is
    */
  def movePlayer(direction: Int): Position =
    currentGame.currentRoom.playerMove(currentGame.currentPosition, direction) match
      case Some(position) =>
        if currentGame.currentPosition != position then
          currentGame.currentRoom.checkMovementConsequences(
            currentGame.currentPosition,
            position
          ) match
            case Success(value)     => currentGame.currentPosition = value
            case Failure(exception) => println(exception)
      case None => checkChangeRoom(direction)
    // update the GUI
    view.associateTiles(currentGame.currentRoom.cells.toList.sorted.map(extractPath))
    currentGame.currentPosition

  /** check if the room is changing, if that is the case performs the necessary actions
    * @param direction
    *   the [[Direction]] in which the player is moving
    */
  def checkChangeRoom(direction: Direction): Unit =
    currentGame.gameMap.changeRoom(currentGame.currentPosition, currentGame.currentRoom.name, direction) match
      case Success((room, pos)) =>
        currentGame.currentRoom = room
        currentGame.currentPosition = pos
        currentGame.startPositionInRoom = pos
      case Failure(_) => () // it is not a link, just do nothing

  /** Reset the current room
    */
  def resetRoom(): Unit =
    val resettedRoom = currentGame.originalGameMap.getRoomFromName(currentGame.currentRoom.name).get

    // la porta non può avere roba sopra quando chiusa (e di default è sempre chiusa)
    val doors = currentGame.currentRoom.cells.collect { case c: DoorCell => c }.map(c => c.copy(cellItem = Item.Empty))

    resettedRoom.updateCells(doors.asInstanceOf[Set[Cell]])

    // prendo le celle dove l'item di potenziamento è stato tolto
    val itemEmpty = for
      c <- currentGame.currentRoom.cells
      r <- resettedRoom.cells
      ri = r.cellItem
      if ri != Item.Box && ri != Item.Empty
      pc = c.position
      pr = r.position
      if pc == pr
      if c.cellItem != ri
    yield r.updateItem(resettedRoom.cells, Item.Empty).filter(e => e.position == r.position).head

    resettedRoom.updateCells(itemEmpty)

    currentGame.currentRoom = resettedRoom
    currentGame.currentPosition = currentGame.startPositionInRoom
    val newRooms =
      currentGame.gameMap.rooms.-(currentGame.gameMap.getRoomFromName(resettedRoom.name).get).+(resettedRoom)
    currentGame.gameMap = new GameMap(
      currentGame.gameMap.name,
      newRooms,
      currentGame.gameMap.initialRoom,
      currentGame.gameMap.initialPosition
    )

object simulate extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/json/testMap.json")
  GameController.startGame(p)
