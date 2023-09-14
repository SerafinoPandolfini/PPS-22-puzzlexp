package utils

import model.cells.{Cell, Direction, Position}

object ConstantUtils:
  val CoinValue = 10
  val BagValue = 20
  val TrunkValue = 50
  val NotValuable = 0
  val AdjacentDirection: List[Position] = List(
    (0, 1),
    (1, 0),
    (0, -1),
    (-1, 0),
    (1, 1),
    (-1, 1),
    (-1, -1),
    (1, -1)
  )
  val genericDirection: Direction = Direction.Down
  val defaultPosition: Position = (0, 0)
