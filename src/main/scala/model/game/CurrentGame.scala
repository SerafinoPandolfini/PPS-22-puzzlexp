package model.game

import controller.GameController
import model.gameMap.GameMap
import model.cells.{Item, Position}
import model.room.Room
import serialization.JsonDecoder
import model.cells.logic.TreasureExtension.mapItemToValue

import scala.util.{Failure, Success}

object CurrentGame:
  private var _scoreCounter: Int = 0
  private var _itemHolder: ItemHolder = ItemHolder(List.empty)
  private var _originalGameMap: GameMap = _
  private var _gameMap: GameMap = _
  private var _currentRoom: Room = _
  private var _currentPosition: Position = _
  private var _startPositionInRoom: Position = _

  def scoreCounter: Int = _scoreCounter

  def itemHolder: ItemHolder = _itemHolder

  def currentRoom: Room = _currentRoom

  def currentPosition: Position = _currentPosition

  def startPositionInRoom: Position = _startPositionInRoom

  def gameMap: GameMap = _gameMap

  def originalGameMap: GameMap = _originalGameMap

  /** initialize all the values that depends on the game map
    * @param value
    *   the map
    */
  def initialize(value: GameMap): Unit =
    _scoreCounter = 0
    _itemHolder = ItemHolder(List.empty)
    _originalGameMap = value
    _gameMap = value
    _currentRoom = value.getRoomFromName(value.initialRoom).get
    _currentPosition = value.initialPosition
    _startPositionInRoom = value.initialPosition

  /** substitute the current room with a new one, updating the current map
    * @param newRoom
    *   the new [[Room]]
    */
  def resetRoom(newRoom: Room): Unit =
    _currentRoom = newRoom
    _currentPosition = _startPositionInRoom
    _gameMap.updateRoom(newRoom)

  /** change the current room
    * @param room
    *   the new room
    * @param pos
    *   the initial position in the new room
    */
  def changeRoom(room: Room, pos: Position): Unit =
    _currentRoom = room
    _currentPosition = pos
    _startPositionInRoom = pos

  /** Adds an item to the [[ItemHolder]] and calculate its score
    * @param item
    *   the item to add
    */
  def addItem(item: Item): Unit =
    if item != Item.Empty then
      _itemHolder = _itemHolder.addItem(item)
      _scoreCounter = _scoreCounter + item.mapItemToValue

  /** perform the actions needed when a player moves and adjust its position
    * @param position
    *   the position of the player after the move
    */
  def checkConsequences(position: Position): Unit =
    _currentRoom.checkMovementConsequences(_currentPosition, position) match
      case Success(value)     => _currentPosition = value
      case Failure(exception) => println(exception)
