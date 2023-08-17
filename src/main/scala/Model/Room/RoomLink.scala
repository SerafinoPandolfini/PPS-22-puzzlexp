package Model.Room

import Model.Cells.Direction

case class RoomLink(from: (Int, Int), direction: Direction, toRoom: String, to: (Int, Int))
