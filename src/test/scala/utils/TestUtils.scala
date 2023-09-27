package utils

import model.room.RoomLink
import model.room.rules.RoomRules
import model.cells.Position
import model.cells.properties.Direction
import model.room.Room
import model.gameMap.GameMap

import java.nio.file.Paths

object TestUtils:
  val DefaultPosition: Position = (0, 0)
  val OutOfBoundPosition: Position = (-1, -1)
  val Position0_1: Position = (0, 1)
  val Position1_0: Position = (1, 0)
  val Position1_1: Position = (1, 1)
  val Position1_2: Position = (1, 2)
  val Position2_1: Position = (2, 1)
  val Position2_2: Position = (2, 2)
  val Position2_3: Position = (2, 3)
  val Position3_1: Position = (3, 1)
  val Position3_2: Position = (3, 2)
  val Position3_3: Position = (3, 3)
  val Position4_1: Position = (4, 1)
  val Position5_1: Position = (5, 1)
  val RoomWidth = 4
  val RoomHeight = 4
  val NoUpdateMessage = "the player should not move and the room should not change"
  val GenericDirection: Direction = Direction.Up
  val LeftLink: RoomLink = RoomLink((0, 3), Direction.Left, "test2", (RoomWidth - 1, 3))
  val CheckExistingFile: String = "src/main/resources/json/testMap.json"
  val CheckWrongFile: String = "src/main/resources/json/ZeroMap.json"
  val JsonTestFile: String = "json/testMap.json"
  val MapName = "mappa 1"
  val RoomName = "room 1"
  val Room2Name = "room 2"
  val FakeRoomName = "pippo"
  val PathSave: String = Paths.get(System.getProperty("user.home"), "puzzlexp", "saves", "testMap.json").toString

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
