package utils

import model.cells.Direction
import model.room.RoomLink
import model.room.rules.RoomRules

object TestUtils:
  val defaultPosition = (0, 0)
  val outOfBoundPosition = (-1, -1)
  val position1_1 = (1, 1)
  val position1_2 = (1, 2)
  val position2_1 = (2, 1)
  val position2_2 = (2, 2)
  val position3_1 = (3, 1)
  val position3_2 = (3, 2)
  val position3_3 = (3, 3)
  val RoomWidth = 4
  val RoomHeight = 4
  val genericDirection: Direction = Direction.Up
  val leftLink: RoomLink = RoomLink((0, 3), Direction.Left, "test2", (RoomWidth - 1, 3))
  val roomRules = RoomRules()
