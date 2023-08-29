package model.game

import model.gameMap.GameMap
import model.cells.Position
import model.room.Room
import serialization.JsonDecoder

object CurrentGame:
  val p: String = JsonDecoder.getAbsolutePath("src/main/resources/json/mapProva.json")
  val gameMap: GameMap = JsonDecoder.mapDecoder(JsonDecoder.getJsonFromPath(p).toOption.get.hcursor).toOption.get
  var currentRoom: Room = gameMap.getRoomFromName(gameMap.initialRoom).get
  var currentPosition: Position = gameMap.initialPosition
  var startPositionInRoom: Position = gameMap.initialPosition
