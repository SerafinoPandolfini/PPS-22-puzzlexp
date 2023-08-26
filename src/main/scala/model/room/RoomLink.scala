package model.room

import model.cells.Direction

case class RoomLink(from: (Int, Int), direction: Direction, toRoom: String, to: (Int, Int))
