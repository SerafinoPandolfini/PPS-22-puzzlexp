package utils

import model.room.Room
import model.cells.*
import model.cells.Cell.given
import prologEngine.PrologConverter.noProperty
import utils.ConstantUtils.AdjacentDirection
import prologEngine.PrologConverter.*
import prologEngine.PrologEngine
import prologEngine.PrologEngine.{*, given}
import alice.tuprolog.{Struct, Term}

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
  def extractPath(cell: Cell, cells: Set[Cell] = Set.empty[Cell]): String = {
    val namePath = PathSplit concat Room.cellToString(cell, true)
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
    * @return
    *   a list with the adjacent cells
    */
  def computeAdjacentCells(cell: Cell): List[Position] =
    AdjacentDirection.map(p => (p._1 + cell.position._1, p._2 + cell.position._2))

  private def extractWallPath(cell: Cell, cells: Set[Cell]): String =
    val adjacentList: List[Cell] = cells.filter(c => computeAdjacentCells(cell) contains c.position).toList
    val adjacentPrologList: List[String] = adjacentList.map(cell => convertCellToProlog(cell, isWall))
    // val engine = PrologEngine("../prologTheory/filter_wall_cells")
    // val input = Struct.of("filter_wall_cells", adjacentPrologList, "wall")
    // val result = engine.solve(input)
    // adjacentPrologList.filter(cell => cell == c(_, _, _, wall)) // c(wl,2,1,wall)
    // println(adjacentPrologList)
    NoPath
    // cercare usando le coordinate le celle attorno a cell
    // ASSICURATI CHE SE è UNA CELLA SUL BORDO DI CONSIDERARE L'OUT OF BOUND COME MURO

    // PROLOG APPROACH
    // usare le celle attorno e convertirle in notazione prolog (dividere celle muro e celle non muro)
    // chiamare il solve prolog
    // convertire il ritorno delle teoria prolog usando i given
    // RICORDA CHE PROLOG DEVE RITORNARE UNA STRINGA LOWERCASE, TU LA DEVI FARE UPPERCASE

// SCALA APPROACH
// dividere celle muro e non muro
// creare costanti per le stringhe mancanti

// return PathSplit + roba prolog
