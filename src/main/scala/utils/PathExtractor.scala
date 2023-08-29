package utils

import model.room.Room
import model.cells.*
import model.cells.Cell.given

object PathExtractor:

  val PathStart = "cell"
  val PathSplit = "_"
  val PathWalkable = "_W"
  val PathDeadly = "_D"
  val NoPath = ""

  def extractPath(cell: Cell): String =
    val namePath = PathSplit concat Room.cellToString(cell, true)
    val walkablePath = cell.walkableState match
      case WalkableType.Walkable(b) => if b then PathWalkable else NoPath
      case WalkableType.DirectionWalkable(_) => cell match
        case cliffCell: CliffCell => PathSplit concat cliffCell.direction.toString.toUpperCase
        case _ => NoPath
    val deadlyPath = if cell.isDeadly then PathDeadly else NoPath

    PathStart concat namePath concat walkablePath concat deadlyPath
