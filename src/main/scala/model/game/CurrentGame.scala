package model.game

import model.gameMap.GameMap
import model.cells.Position
import model.room.Room
import serialization.JsonDecoder

object CurrentGame:
  private var _currentRoom: Room = _
  private var _currentPosition: Position = _
  private var _startPositionInRoom: Position = _
  private var _gameMap: GameMap = _
  private var _originalGameMap: GameMap = _

  def currentRoom: Room = _currentRoom
  def currentRoom_=(value: Room): Unit = _currentRoom = value
  def currentPosition: Position = _currentPosition
  def currentPosition_=(value: Position): Unit = _currentPosition = value
  def startPositionInRoom: Position = _startPositionInRoom
  def startPositionInRoom_=(value: Position): Unit = _startPositionInRoom = value
  def gameMap: GameMap = _gameMap
  def gameMap_=(value: GameMap): Unit = _gameMap = value
  def originalGameMap: GameMap = _originalGameMap
  def originalGameMap_=(value: GameMap): Unit = _originalGameMap = value
