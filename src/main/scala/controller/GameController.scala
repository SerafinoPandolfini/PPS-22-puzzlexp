package controller

import model.cells.logic.UseItemExtension.usePowerUp
import model.cells.logic.CellExtension.*
import model.cells.logic.TreasureExtension.*
import model.cells.*
import model.cells.Item.{GoalGem, Empty}
import model.game.{CurrentGame, ItemHolder}
import model.gameMap.GameMap
import model.room.{Room, RoomBuilder}
import serialization.{JsonDecoder, JsonEncoder}
import utils.PositionExtension.+
import utils.KeyDirectionMapping.given
import utils.PathExtractor.extractPath
import view.GameView
import utils.Percentage.%%
import java.io.PrintWriter
import io.circe.Json
import java.awt.event.KeyEvent
import scala.util.{Failure, Success}
import java.nio.file.*

object GameController:

  private var _view: GameView = _

  def view: GameView = _view

  /** performs the necessary actions to initialize and start the game
    * @param path
    *   the path of the json for the current game
    */
  def startGame(path: String): Unit =
    val jsonData = JsonDecoder.getJsonFromPath(path).toOption.get
    if Path.of(path).startsWith(Paths.get("src/main/resources/saves/").toAbsolutePath) then
      CurrentGame.load(jsonData)
    else
      CurrentGame.initialize(
        JsonDecoder.mapDecoder(jsonData.hcursor).toOption.get
      )
    println(CurrentGame.originalGameMap.totalPoints)
    _view = GameView(CurrentGame.currentRoom, CurrentGame.currentPosition)
    updateToolbarLabels()

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
      case None => checkChangeRoom(direction)
    checkMoveOnItem()
    view.associateTiles(CurrentGame.currentRoom)
    var dir = direction
    if CurrentGame.currentRoom.isPlayerDead(CurrentGame.currentPosition).toOption.get then
      resetRoom()
      dir = KeyEvent.VK_S
    view.updatePlayerImage(CurrentGame.currentPosition, dir)
    updateToolbarLabels()

  private def updateToolbarLabels(): Unit =
    val itemCounts: Map[Item, Int] = Item.values.map(item => item -> 0).toMap ++ CurrentGame.itemHolder.itemOwned
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap
    var score = 0
    itemCounts.foreach { case (item, count) =>
      view.updateItemLabel(item, count)
      if item.isTreasure then score = score + item.mapItemToValue * count
    }
    view.updateScore(score)

  /** Check if the player has moved on a collectable item and performs the needed actions
    */
  private def checkMoveOnItem(): Unit =
    CurrentGame.currentRoom.getCell(CurrentGame.currentPosition) match
      case Some(value) =>
        value.cellItem match
          case GoalGem =>
            _view.endGame(
              CurrentGame.scoreCounter,
              CurrentGame.originalGameMap.totalPoints,
              CurrentGame.scoreCounter.toDouble %% CurrentGame.originalGameMap.totalPoints.toDouble
            )
          case Empty => () // do nothing, it's empty
          case _ =>
            CurrentGame.currentRoom.updateCells(value.updateItem(CurrentGame.currentRoom.cells, Item.Empty))
            CurrentGame.addItem(value.cellItem)
      case None => ()

  /** check if the room is changing, if that is the case performs the necessary actions
    * @param direction
    *   the [[Direction]] in which the player is moving
    */
  private def checkChangeRoom(direction: Direction): Unit =
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
    val doors = CurrentGame.currentRoom.cells.collect { case c: LockCell => c }.map(c => c.copy(cellItem = Item.Empty))

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

  /** saves the actual game writing the needed info in a json file
    */
  def saveGame(): Unit =
    val json: Json = JsonEncoder.saveGameEncoder.apply(CurrentGame)
    val outputFile = new java.io.File(s"src/main/resources/saves/${CurrentGame.originalGameMap.name}.json")
    val printWriter = new PrintWriter(outputFile)
    printWriter.write(json.spaces2)
    printWriter.close()

object simulate extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/json/FirstMap.json")
  GameController.startGame(p)

object useSave extends App:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/saves/FirstMap.json")
  GameController.startGame(p)
