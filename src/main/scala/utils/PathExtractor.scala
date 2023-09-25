package utils

import model.room.Room
import model.cells.*
import prologEngine.PrologConverter.noProperty
import utils.constants.GraphicManager
import prologEngine.PrologConverter.*
import prologEngine.PrologEngine
import prologEngine.PrologEngine.{*, given}
import alice.tuprolog.{Struct, Term}
import model.cells.properties.{Colorable, PressableState, WalkableType}
import model.cells.traits.CoveredHole
import utils.constants.GraphicManager.AdjacentDirection
import utils.extensions.RoomCellsRepresentation.cellToString

object PathExtractor:

  val PathStart: String = "cell"
  val PathSplit: String = "_"
  val PathWalkable: String = "_W"
  val PathDeadly: String = "_D"
  val PathCovered: String = "_C"
  val PathPressed: String = "_P"
  val NoPath: String = ""
  val lowerAdjacentBound: Int = 0
  val upperAdjacentBound: Int = 2
  val offsetFactor: Int = 1
  val validCorners: List[List[(Int, Int)]] = List(
    List((0, 0), (0, 1), (1, 0)),
    List((1, 0), (2, 0), (2, 1)),
    List((0, 1), (0, 2), (1, 2)),
    List((1, 2), (2, 1), (2, 2))
  )

  /** @param cell
    *   the cell from which the path is extracted
    * @return
    *   a path that match the name of an image file
    */
  def extractPath(cell: Cell, cells: Set[Cell] = Set.empty[Cell]): String = {
    val namePath = PathSplit concat cellToString(cell, true)
    val walkablePath = extractWalkablePath(cell)
    val coveredPath = extractCoveredPath(cell)
    val colorPath = extractColorPath(cell)
    val pressedPath = extractPressedPath(cell)
    val deadlyPath = if (cell.isDeadly) PathDeadly else NoPath
    val wallPath = extractWallPath(cell, cells)

    s"$PathStart$namePath$walkablePath$deadlyPath$coveredPath$colorPath$pressedPath$wallPath"
  }

  private def extractWalkablePath(cell: Cell): String = cell.walkableState match {
    case WalkableType.Walkable(b) => if (b) PathWalkable else NoPath
    case WalkableType.DirectionWalkable(_) =>
      cell match {
        case cliffCell: CliffCell => s"$PathSplit${cliffCell.direction.toString.toUpperCase}"
        case _                    => NoPath
      }
  }

  private def extractCoveredPath(cell: Cell): String = cell match {
    case coveredCell: Cell with CoveredHole => if (coveredCell.cover) PathCovered else NoPath
    case _                                  => NoPath
  }

  private def extractColorPath(cell: Cell): String = cell match {
    case colorCell: Cell with Colorable => s"$PathSplit${colorCell.color.toString.toUpperCase}"
    case _                              => NoPath
  }

  private def extractPressedPath(cell: Cell): String = cell match {
    case buttonCell: ButtonCell => if (buttonCell.pressableState == PressableState.Pressed) PathPressed else NoPath
    case _                      => NoPath
  }

  /** @param cell
    *   the cell that needs the pathString
    * @param cells
    *   the set with all the cells of the game
    * @return
    *   the pathString of cell
    */
  private def extractWallPath(cell: Cell, cells: Set[Cell]): String = cell match
    case cell: WallCell =>
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
