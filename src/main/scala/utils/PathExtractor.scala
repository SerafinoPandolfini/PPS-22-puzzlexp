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
  val PathPressed = "_P"
  val NoPath = ""

  /** @param cell
    *   the cell from which the path is extracted
    * @return
    *   a path that match the name of an image file
    */
  def extractPath(cell: Cell): String = {
    val namePath = PathSplit concat Room.cellToString(cell, true)
    val walkablePath = extractWalkablePath(cell)
    val coveredPath = extractCoveredPath(cell)
    val colorPath = extractColorPath(cell)
    val pressedPath = extractPressedPath(cell)
    val deadlyPath = if (cell.isDeadly) PathDeadly else NoPath

    s"$PathStart$namePath$walkablePath$deadlyPath$coveredPath$colorPath$pressedPath"
  }

  private def extractWalkablePath(cell: Cell): String = cell.walkableState match {
    case WalkableType.Walkable(b) => if (b) PathWalkable else NoPath
    case WalkableType.DirectionWalkable(_) => cell match {
      case cliffCell: CliffCell => s"$PathSplit${cliffCell.direction.toString.toUpperCase}"
      case _ => NoPath
    }
  }

  private def extractCoveredPath(cell: Cell): String = cell match {
    case coveredCell: Cell with CoveredHole => if (coveredCell.cover) PathCovered else NoPath
    case _ => NoPath
  }

  private def extractColorPath(cell: Cell): String = cell match {
    case colorCell: Cell with Colorable => s"$PathSplit${colorCell.color.toString.toUpperCase}"
    case _ => NoPath
  }

  private def extractPressedPath(cell: Cell): String = cell match {
    case buttonCell: ButtonCell => if (buttonCell.pressableState == PressableState.Pressed) PathPressed else NoPath
    case _ => NoPath
  }
