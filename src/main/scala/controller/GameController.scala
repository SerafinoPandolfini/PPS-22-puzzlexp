package controller

import model.cells.logic.PowerUpExtension
import model.cells.logic.CellExtension.*
import model.cells.*
import model.game.CurrentGame
import model.gameMap.GameMap
import model.room.{Room, RoomBuilder}
import serialization.{JsonDecoder, JsonEncoder}
import utils.PositionExtension.+
import utils.KeyDirectionMapping.given
import utils.PathExtractor.extractPath
import view.GameView

import java.awt.event.KeyEvent
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
  def movePlayer(direction: Int): Unit =
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
    view.associateTiles(currentGame.currentRoom)
    var dir = direction
    if CurrentGame.currentRoom.isPlayerDead(currentGame.currentPosition).toOption.get then
      resetRoom()
      dir = KeyEvent.VK_S
    view.updatePlayerImage(currentGame.currentPosition, dir)

  /** check if the room is changing, if that is the case performs the necessary actions
    * @param direction
    *   the [[Direction]] in which the player is moving
    */
  def checkChangeRoom(direction: Direction): Unit =
    currentGame.gameMap.changeRoom(currentGame.currentPosition, currentGame.currentRoom.name, direction) match
      case Success((room, pos)) =>
        resetRoom()
        currentGame.currentRoom = room
        currentGame.currentPosition = pos
        currentGame.startPositionInRoom = pos
      case Failure(_) => () // it is not a link, just do nothing

  /** Reset the current room
    */
  def resetRoom(): Unit =
    val resettedRoom = currentGame.originalGameMap.getRoomFromName(currentGame.currentRoom.name).get
    // reset door
    val doors = currentGame.currentRoom.cells.collect { case c: DoorCell => c }.map(c => c.copy(cellItem = Item.Empty))
    resettedRoom.updateCells(doors.asInstanceOf[Set[Cell]])
    // reset items
    val itemEmpty = for
      c <- currentGame.currentRoom.cells
      r <- resettedRoom.cells
      if r.cellItem != Item.Box && r.cellItem != Item.Empty
      if c.position == r.position
      if c.cellItem != r.cellItem
    yield r.updateItem(resettedRoom.cells, Item.Empty).filter(e => e.position == r.position).head
    resettedRoom.updateCells(itemEmpty)
    // updating
    updateCurrents(resettedRoom)
    view.associateTiles(currentGame.currentRoom)

  /** substitute the current room with a new one, updating the current map
    * @param newRoom
    *   the new [[Room]]
    */
  private def updateCurrents(newRoom: Room): Unit =
    currentGame.currentRoom =
      RoomBuilder().name(newRoom.name).addLinks(newRoom.links.head).addCells(newRoom.cells).build
    currentGame.currentPosition = currentGame.startPositionInRoom
    val newRooms = currentGame.gameMap.rooms
      - currentGame.gameMap.getRoomFromName(currentGame.currentRoom.name).get
      + currentGame.currentRoom
    currentGame.gameMap = GameMap(
      currentGame.gameMap.name,
      newRooms,
      currentGame.gameMap.initialRoom,
      currentGame.gameMap.initialPosition
    )

object simulate extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/json/testMap.json")
  GameController.startGame(p)
