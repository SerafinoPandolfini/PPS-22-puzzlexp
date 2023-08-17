package Model.GameMap

import Model.Cells.Position
import Model.Room.Room

class GameMap(val name: String, val rooms: Set[Room], val initialRoom: String, val initialPosition: Position)
