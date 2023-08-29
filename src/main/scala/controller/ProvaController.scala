package controller

import model.cells.logic.PowerUpExtension
import model.cells.{Cell, Direction, DoorCell, Item, Position}
import model.game.CurrentGame
import model.gameMap.GameMap
import model.room.{Room, RoomBuilder}
import serialization.JsonDecoder
import utils.PositionExtension.+
import utils.KeyDirectionMapping.given

import scala.util.{Failure, Success}

object ProvaController:

  val currentGame: CurrentGame.type = CurrentGame

  /** Performs the actions needed to move the player
    * @param direction
    *   the [[Direction]] in which the player is moving
    * @return
    *   the [[Position]] in which the player is
    */
  def movePlayer(direction: Int): Position =
    currentGame.currentRoom.playerMove(currentGame.currentPosition, direction) match
      case Some(_) =>
        currentGame.currentRoom.checkMovementConsequences(
          currentGame.currentPosition,
          currentGame.currentPosition + direction.coordinates
        ) match
          case Success(value)     => currentGame.currentPosition = value
          case Failure(exception) => println(exception)
      case None => checkChangeRoom(direction)
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
