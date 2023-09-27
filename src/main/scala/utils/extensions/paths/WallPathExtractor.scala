package utils.extensions.paths

import model.cells.{WallCell, Cell}
import utils.extensions.paths.PathValue.{NoPath, validCorners, lowerAdjacentBound, upperAdjacentBound, offsetFactor}

object WallPathExtractor:

  /** @param cell
    *   the cell that needs the pathString
    * @param cells
    *   the set with all the cells of the game
    * @return
    *   the pathString of cell
    */
  private[paths] def extractWallPath(cell: Cell, cells: Set[Cell]): String = cell match
    case _: WallCell =>
      val adjacentCells: Map[(Int, Int), Boolean] = (for
        y <- lowerAdjacentBound to upperAdjacentBound
        x <- lowerAdjacentBound to upperAdjacentBound
        yOffset = y + cell.position._2 - offsetFactor
        xOffset = x + cell.position._1 - offsetFactor
        c = cells.find(_.position == (xOffset, yOffset))
      yield (x, y) -> c.forall {
        case _: WallCell => true
        case _           => false
      }).toMap
      computeWallType(adjacentCells)
    case _ => NoPath

  /** check if the cell is a particular configuration of wall based on how many corners are there in adjacent cells.
    * List((0, 0), (0, 1), (1, 0)) is TopLeft corner List((1, 0), (2, 0), (2, 1)) is TopRight corner List((0, 1), (0,
    * 2), (1, 2)) is BottomLeft corner List((1, 2), (2, 1), (2, 2)) is BottomRight corner
    *
    * @param adjacentCells
    *   the map with the adjacent cells and a boolean. It's true if the cell is a [[WallCell]]
    * @return
    *   the pathString of cell
    */
  private def computeWallType(adjacentCells: Map[(Int, Int), Boolean]): String =
    validCorners.map(pattern => pattern.forall(adjacentCells.getOrElse(_, false))) match
      case List(true, true, true, true)    => "_C"
      case List(true, true, true, false)   => "_INW"
      case List(true, true, false, true)   => "_INE"
      case List(true, false, true, true)   => "_ISW"
      case List(false, true, true, true)   => "_ISE"
      case List(true, true, false, false)  => "_S"
      case List(true, false, true, false)  => "_E"
      case List(false, true, false, true)  => "_W"
      case List(false, false, true, true)  => "_N"
      case List(true, false, false, false) => "_SE"
      case List(false, true, false, false) => "_SW"
      case List(false, false, true, false) => "_NE"
      case List(false, false, false, true) => "_NW"
      case _                               => "_A"
