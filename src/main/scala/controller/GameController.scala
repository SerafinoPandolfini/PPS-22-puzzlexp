package controller

import model.cells.logic.PowerUpExtension
import model.cells.*
import model.game.CurrentGame
import model.gameMap.GameMap
import model.room.{Room, RoomBuilder}
import serialization.{JsonDecoder, JsonEncoder}
import utils.PositionExtension.+
import utils.KeyDirectionMapping.given
import utils.PathExtractor.extractPath
import view.GameView

import scala.util.{Failure, Success}

object GameController:

  val currentGame: CurrentGame.type = CurrentGame
  private var gameMap: GameMap = _
  private var _view: GameView = _

  def view: GameView = _view

  def startGame(mapPath: String): Unit =
    gameMap = JsonDecoder.mapDecoder(JsonDecoder.getJsonFromPath(mapPath).toOption.get.hcursor).toOption.get
    currentGame.currentRoom = gameMap.getRoomFromName(gameMap.initialRoom).get
    currentGame.startPositionInRoom = gameMap.initialPosition
    currentGame.currentPosition = gameMap.initialPosition
    _view = GameView(currentGame.currentRoom, gameMap.initialPosition)

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
    view.associateTiles(currentGame.currentRoom)
    currentGame.currentPosition

  /** check if the room is changing, if that is the case performs the necessary actions
    * @param direction
    *   the [[Direction]] in which the player is moving
    */
  def checkChangeRoom(direction: Direction): Unit =
    gameMap.changeRoom(currentGame.currentPosition, currentGame.currentRoom.name, direction) match
      case Success((room, pos)) =>
        currentGame.currentRoom = room
        currentGame.currentPosition = pos
        currentGame.startPositionInRoom = pos
      case Failure(_) => () // it is not a link, just do nothing

object simulate extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/json/testMap.json")
  GameController.startGame(p)
