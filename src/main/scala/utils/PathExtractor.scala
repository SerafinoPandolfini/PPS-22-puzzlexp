package utils

import model.room.Room
import model.cells.*
import model.cells.Cell.given

object PathExtractor:

  val PathStart = "cell"
  val PathSplit = "_"
  val PathWalkable = "_W"
  val PathDeadly = "_D"
  val PathCovered = "_C"
  val NoPath = ""

  /** @param cell
    *   the cell from which the path is extracted
    * @return
    *   a path that match the name of an image file
    */
  def extractPath(cell: Cell): String =
    val namePath = PathSplit concat Room.cellToString(cell, true)
    val walkablePath = cell.walkableState match
      case WalkableType.Walkable(b) => if b then PathWalkable else NoPath
      case WalkableType.DirectionWalkable(_) =>
        cell match
          case cliffCell: CliffCell => s"$PathSplit${cliffCell.direction.toString.toUpperCase}"
          case _                    => NoPath
    val covered = cell match
      case coveredCell: Cell with CoveredHole => if coveredCell.cover then PathCovered else NoPath
      case _                                  => NoPath

    val color = cell match
      case colorCell: Cell with Colorable => s"$PathSplit${colorCell.color.toString.toUpperCase}"
      case _                              => NoPath

    val deadlyPath = if cell.isDeadly then PathDeadly else NoPath

    s"$PathStart$namePath$walkablePath$deadlyPath$covered$color"
