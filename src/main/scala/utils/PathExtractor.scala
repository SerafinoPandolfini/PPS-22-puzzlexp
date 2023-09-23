package utils

import model.room.Room
import model.cells.*
import model.cells.Cell.given
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
import utils.givens.CellMapping.*

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
    * @return
    *   a list with the adjacent position of cell
    */
  def computeAdjacentPositions(cell: Cell): List[Position] =
    AdjacentDirection.map(p => (p._1 + cell.position._1, p._2 + cell.position._2)).toList

  /** @param positions
    *   the list with the adjacent positions
    * @param cells
    *   the set with all the cells of the game
    * @return
    *   a set with the adjacent cells. Bounds are excluded
    */
  private def computeAdjacentList(positions: List[Position], cells: Set[Cell]): Set[Cell] =
    cells.filter(c => positions contains c.position)

  /** @param cell
    *   the cell that needs the pathString
    * @param adjacentCellsList
    *   the set with the adjacent cells of cell without the bounds
    * @return
    *   a set with the adjacent cells mapped in a 3x3 matrix in which bounds are added.
    */
  private def mapCellsWithBounds(cell: Cell, adjacentCellsList: Set[Cell]): Set[Cell] =
    val mappedCells: Set[Cell] = cell.convertCellsPositions(adjacentCellsList)
    val mappedCellsWithBound: Set[Cell] = mappedCells ++ (for
      pos <- AdjacentDirection
      if !mappedCells.exists(c => c.position == pos)
    yield WallCell(position = pos)).toSet
    mappedCellsWithBound

  /** @param cell
    *   the cell that needs the pathString
    * @param cells
    *   the set with all the cells of the game
    * @return
    *   the pathString of cell
    */
  private def extractWallPath(cell: Cell, cells: Set[Cell]): String =
    val adjacentCellsList: Set[Cell] = computeAdjacentList(
      computeAdjacentPositions(cell),
      cells
    )
    val adjacentPrologList: List[String] =
      mapCellsWithBounds(cell, adjacentCellsList).map(cell => convertCellToProlog(cell, isWall)).toList
    println("->" + adjacentPrologList)
    val engine = PrologEngine("/prologTheory/filter_wall_cells_and_check_corners.pl")
    val input = Struct.of("filter_wall_and_check_corners", adjacentPrologList, "Path") // soluzione
    val adjacentWall = engine.solve(input, "Path") // nomi var che deve ritornare //metti come cost
    // string è filtered, e term è la lista. poi ci sarà il pezzo da aggiungere al file
    // in prolong engine fai conversion da term a string

    s"$PathSplit${adjacentWall("Path").toString.toUpperCase}" // testo undercase di quello che va aggiunto alla fine deò file

    // due proprietà prolog che is chiamano a vicenda

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
