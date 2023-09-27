package controller.game

import controller.game.GameController.{_view, resetRoom}
import model.cells.properties.Item.{Empty, GoalGem}
import model.cells.logic.CellExtension.*
import model.cells.properties.{Direction, Item}
import model.game.CurrentGame
import utils.extensions.Percentage.%%
import view.game.ViewUpdater.endGame

import scala.util.{Failure, Success}

object PlayerMovement:

  /** Check if the player has moved on a collectable item and performs the needed actions */
  private[controller] def checkMoveOnItem(): Unit =
    CurrentGame.currentRoom.getCell(CurrentGame.currentPosition) match
      case Some(value) =>
        value.cellItem match
          case GoalGem =>
            _view.endGame(
              CurrentGame.scoreCounter,
              CurrentGame.originalGameMap.totalPoints,
              CurrentGame.scoreCounter.toDouble %% CurrentGame.originalGameMap.totalPoints.toDouble
            )
          case Empty => ()
          case _ =>
            CurrentGame.currentRoom.updateCells(value.updateItem(CurrentGame.currentRoom.cells, Item.Empty))
            CurrentGame.addItem(value.cellItem)
      case None => ()

  /** check if the room is changing, if that is the case performs the necessary actions
    * @param direction
    *   the [[Direction]] in which the player is moving
    */
  private[controller] def checkChangeRoom(direction: Direction): Unit =
    CurrentGame.gameMap.changeRoom(CurrentGame.currentPosition, CurrentGame.currentRoom.name, direction) match
      case Success((room, pos)) =>
        resetRoom()
        CurrentGame.changeRoom(room, pos)
      case Failure(exception) => println(s" Exception : $exception ")
