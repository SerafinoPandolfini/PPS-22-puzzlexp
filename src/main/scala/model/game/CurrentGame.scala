package model.game

import model.gameMap.{GameMap, MinimapElement}
import model.cells.Position
import model.room.Room
import utils.givens.ItemConversion.given_Conversion_Item_Int
import io.circe.{HCursor, Json}
import model.cells.properties.Item
import serialization.JsonDecoder
import model.gameMap.Minimap.createMinimap
import scala.util.{Failure, Success}

object CurrentGame:
  private var _scoreCounter: Int = 0
  private var _itemHolder: ItemHolder = ItemHolder(List.empty)
  private var _originalGameMap: GameMap = _
  private var _gameMap: GameMap = _
  private var _currentRoom: Room = _
  private var _currentPosition: Position = _
  private var _startPositionInRoom: Position = _
  private var _miniMapElement: List[MinimapElement] = _

  def scoreCounter: Int = _scoreCounter

  def itemHolder: ItemHolder = _itemHolder

  def currentRoom: Room = _currentRoom

  def currentPosition: Position = _currentPosition

  def startPositionInRoom: Position = _startPositionInRoom

  def gameMap: GameMap = _gameMap

  def originalGameMap: GameMap = _originalGameMap

  def minimapElement: List[MinimapElement] = _miniMapElement

  /** initialize all the values that depends on the game map
    * @param value
    *   the map
    */
  def initialize(value: GameMap): Unit =
    _scoreCounter = 0
    _itemHolder = ItemHolder(List.empty)
    _originalGameMap = value
    _gameMap = value.createCopy()
    _currentRoom = value.getRoomFromName(value.initialRoom).get
    _currentPosition = value.initialPosition
    _startPositionInRoom = value.initialPosition
    _miniMapElement = _gameMap.createMinimap()
    _miniMapElement = visitRoom()

  /** substitute the current room with a new one, updating the current map
    * @param newRoom
    *   the new [[Room]]
    */
  def resetRoom(newRoom: Room): Unit =
    _currentRoom = newRoom
    _currentPosition = _startPositionInRoom
    _gameMap.rooms = _gameMap.updateRoom(newRoom)

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
    _miniMapElement = visitRoom()

  /** Adds an item to the [[ItemHolder]] and calculate its score
    * @param item
    *   the item to add
    */
  def addItem(item: Item): Unit =
    if item != Item.Empty then
      _itemHolder = _itemHolder.addItem(item)
      _scoreCounter = _scoreCounter + item

  /** remove the specified item
    * @param item
    *   the item to remove
    */
  def removeItem(item: Item): Unit = _itemHolder = _itemHolder.removeItem(item)

  /** perform the actions needed when a player moves and adjust its position
    * @param position
    *   the position of the player after the move
    */
  def checkConsequences(position: Position): Unit =
    _currentRoom.checkMovementConsequences(_currentPosition, position) match
      case Success(value)     => _currentPosition = value
      case Failure(exception) => println(exception)

  /** load a saved game
    * @param json
    *   the [[Json]] file with the info of the saved game
    */
  def load(json: Json): Unit =
    JsonDecoder.saveGameDecoder.apply(json.hcursor) match
      case Right(originalMap, currentMap, room, currentPosition, startPosition, itemList, score, minimap) =>
        _scoreCounter = score
        _itemHolder = ItemHolder(itemList)
        JsonDecoder.getJsonFromPath(originalMap) match
          case Success(value) =>
            JsonDecoder.mapDecoder(value.hcursor) match
              case Right(map) => _originalGameMap = map
              case Left(ex)   => println(ex)
          case Failure(exception) => println(exception)
        _gameMap = currentMap
        _currentRoom = room
        _currentPosition = currentPosition
        _startPositionInRoom = startPosition
        _miniMapElement = minimap
      case Left(ex) => println(ex)

  /** change the minimap marking the current room as visited
    * @return
    *   the modified minimap
    */
  private def visitRoom(): List[MinimapElement] =
    _miniMapElement.map(element => if element.name == _currentRoom.name then element.visit() else element)
