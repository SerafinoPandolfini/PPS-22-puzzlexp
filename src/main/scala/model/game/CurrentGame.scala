package model.game

import controller.GameController
import model.gameMap.GameMap
import model.cells.{Position, Item}
import model.room.Room
import serialization.JsonDecoder
import utils.ItemConversion.given_Conversion_Item_Int

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

  def currentPosition_=(value: Position): Unit = _currentPosition = value

  def startPositionInRoom: Position = _startPositionInRoom

  def gameMap: GameMap = _gameMap

  def originalGameMap: GameMap = _originalGameMap

  def initialize(value: GameMap): Unit =
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

  def changeRoom(room: Room, pos: Position): Unit =
    _currentRoom = room
    _currentPosition = pos
    _startPositionInRoom = pos

  def addItem(item: Item): Unit =
    if item != Item.Empty then
      _itemHolder = _itemHolder.addItem(item)
      _scoreCounter = _scoreCounter + item
