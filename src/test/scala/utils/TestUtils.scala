package utils

import model.cells.Direction
import model.room.RoomLink
import model.room.rules.RoomRules
import model.cells.Position

object TestUtils:
  val defaultPosition: Position = (0, 0)
  val outOfBoundPosition: Position = (-1, -1)
  val position1_1: Position = (1, 1)
  val position1_2: Position = (1, 2)
  val position2_1: Position = (2, 1)
  val position2_2: Position = (2, 2)
  val position3_1: Position = (3, 1)
  val position3_2: Position = (3, 2)
  val position3_3: Position = (3, 3)
  val RoomWidth = 4
  val RoomHeight = 4
  val genericDirection: Direction = Direction.Up
  val leftLink: RoomLink = RoomLink((0, 3), Direction.Left, "test2", (RoomWidth - 1, 3))
  val roomRules: RoomRules = RoomRules()
