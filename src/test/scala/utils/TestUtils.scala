package utils

import model.room.RoomLink
import model.room.rules.RoomRules
import model.cells.Position
import model.cells.properties.Direction
import model.room.Room
import model.gameMap.GameMap

object TestUtils:
  val defaultPosition: Position = (0, 0)
  val outOfBoundPosition: Position = (-1, -1)
  val position1_1: Position = (1, 1)
  val position1_2: Position = (1, 2)
  val position1_3: Position = (1, 3)
  val position2_1: Position = (2, 1)
  val position2_2: Position = (2, 2)
  val position2_3: Position = (2, 3)
  val position3_1: Position = (3, 1)
  val position3_2: Position = (3, 2)
  val position3_3: Position = (3, 3)
  val RoomWidth = 4
  val RoomHeight = 4
  val genericDirection: Direction = Direction.Up
  val leftLink: RoomLink = RoomLink((0, 3), Direction.Left, "test2", (RoomWidth - 1, 3))
  val roomRules: RoomRules = RoomRules()
  val CheckExistingFile: String = "src/main/resources/json/testMap.json"
  val CheckWrongFile: String = "src/main/resources/json/ZeroMap.json"

  def isEqual(room1: Room, room2: Room): Boolean =
    room2.name == room1.name &&
      room2.links == room1.links &&
      room2.cells == room1.cells

  def isEqual(map1: GameMap, map2: GameMap): Boolean =
    val numbereq = for
      m2 <- map2.rooms
      m1 <- map1.rooms
      if isEqual(m1, m2)
    yield m2
    map2.name == map1.name &&
    map2.rooms.size == map1.rooms.size &&
    numbereq.size == map1.rooms.size &&
    map2.initialRoom == map1.initialRoom &&
    map2.initialPosition == map1.initialPosition
