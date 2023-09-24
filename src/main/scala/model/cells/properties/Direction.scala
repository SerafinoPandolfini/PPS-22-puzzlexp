package model.cells.properties

import model.cells.Position

/** Enumeration representing the direction of movement of a player or item
  */
enum Direction(val coordinates: Position):
  case Left extends Direction((-1, 0))
  case Right extends Direction((1, 0))
  case Up extends Direction((0, -1))
  case Down extends Direction((0, 1))

  /** @return
    *   the opposite direction
    */
  def opposite: Direction = this match
    case Left  => Right
    case Right => Left
    case Up    => Down
    case Down  => Up
