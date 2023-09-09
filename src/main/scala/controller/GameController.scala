package controller

import model.cells.logic.UseItemExtension.usePowerUp
import model.cells.logic.CellExtension.*
import model.cells.logic.TreasureExtension.*
import model.cells.*
import model.game.{CurrentGame, ItemHolder}
import model.gameMap.GameMap
import model.room.{Room, RoomBuilder}
import serialization.{JsonDecoder, JsonEncoder}
import utils.PositionExtension.+
import utils.KeyDirectionMapping.given
import utils.PathExtractor.extractPath
import view.GameView
import utils.ItemConversion.given

import java.awt.event.KeyEvent
import scala.util.{Failure, Success}

object GameController:

  private var _view: GameView = _

  def view: GameView = _view

  def startGame(mapPath: String): Unit =
    CurrentGame.initialize(
      JsonDecoder.mapDecoder(JsonDecoder.getJsonFromPath(mapPath).toOption.get.hcursor).toOption.get
    )
    _view = GameView(CurrentGame.currentRoom, CurrentGame.gameMap.initialPosition)

  /** Performs the actions needed to move the player
    * @param direction
    *   the [[Direction]] in which the player is moving
    * @return
    *   the [[Position]] in which the player is
    */
  def movePlayer(direction: Int): Unit =
    CurrentGame.currentRoom.playerMove(CurrentGame.currentPosition, direction) match
      case Some(position) =>
        if CurrentGame.currentPosition != position then
          CurrentGame.currentRoom.checkMovementConsequences(
            CurrentGame.currentPosition,
            position
          ) match
            case Success(value)     => CurrentGame.currentPosition = value
            case Failure(exception) => println(exception)
        else
          println(CurrentGame.currentRoom.getCell(position + direction.coordinates).get)
          println(CurrentGame.itemHolder.itemOwned)
          CurrentGame.currentRoom.updateCells(
            CurrentGame.currentRoom.getCell(position + direction.coordinates).get.usePowerUp()
          )
      case None => checkChangeRoom(direction)
    // update the GUI
    checkMoveOnItem()
    view.associateTiles(CurrentGame.currentRoom)
    var dir = direction
    if CurrentGame.currentRoom.isPlayerDead(CurrentGame.currentPosition).toOption.get then
      resetRoom()
      dir = KeyEvent.VK_S
    view.updatePlayerImage(CurrentGame.currentPosition, dir)
    updateToolbarLabels()

  private def updateToolbarLabels(): Unit =
    val itemCounts: Map[Item, Int] = CurrentGame.itemHolder.itemOwned.groupBy(identity).view.mapValues(_.size).toMap
    var score = 0
    itemCounts.foreach { case (item, count) =>
      view.updateItemLabel(item, count)
      if item.isTreasure then score = score + item * count
    }
    view.updateScore(score)

  private def checkMoveOnItem(): Unit =
    CurrentGame.currentRoom.getCell(CurrentGame.currentPosition) match
      case Some(value) =>
        CurrentGame.currentRoom.updateCells(value.updateItem(CurrentGame.currentRoom.cells, Item.Empty))
        CurrentGame.addItem(value.cellItem)
      case None => ()

  /** check if the room is changing, if that is the case performs the necessary actions
    * @param direction
    *   the [[Direction]] in which the player is moving
    */
  def checkChangeRoom(direction: Direction): Unit =
    CurrentGame.gameMap.changeRoom(CurrentGame.currentPosition, CurrentGame.currentRoom.name, direction) match
      case Success((room, pos)) =>
        resetRoom()
        CurrentGame.changeRoom(room, pos)
      case Failure(_) => () // it is not a link, just do nothing

  /** Reset the current room
    */
  def resetRoom(): Unit =
    val resettedRoom = CurrentGame.originalGameMap.getRoomFromName(CurrentGame.currentRoom.name).get
    // reset door
    val doors = CurrentGame.currentRoom.cells.collect { case c: DoorCell => c }.map(c => c.copy(cellItem = Item.Empty))
    resettedRoom.updateCells(doors.asInstanceOf[Set[Cell]])
    // reset items
    val itemEmpty = for
      c <- CurrentGame.currentRoom.cells
      r <- resettedRoom.cells
      if r.cellItem != Item.Box && r.cellItem != Item.Empty
      if c.position == r.position
      if c.cellItem != r.cellItem
    yield r.updateItem(resettedRoom.cells, Item.Empty).filter(e => e.position == r.position).head
    resettedRoom.updateCells(itemEmpty)
    CurrentGame.resetRoom(resettedRoom)
    view.associateTiles(CurrentGame.currentRoom)

object simulate extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/json/testMap.json")
  GameController.startGame(p)
