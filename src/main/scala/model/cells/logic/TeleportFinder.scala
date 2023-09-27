package model.cells.logic

import model.cells.*
import prologEngine.PrologEngine
import prologEngine.PrologEngine.{*, given}
import alice.tuprolog.{Struct, Term}
import prologEngine.PrologConverter.*
import utils.constants.PathManager.{PrologExtension, TheoryDirectoryPath}

object TeleportFinder:
  val TermX = "X"
  val TermY = "Y"
  val TheoryName = "search_teleport_destination"

  /** Find the teleport destination cell from the specified set
    * @param cells
    *   the set of cells to fìlter
    * @return
    *   an option representing the position of the teleport destination cell
    */
  def findDestination(cells: Set[Cell]): Option[Position] =
    val set = cells.map(convertCellToProlog(_))
    val engine = PrologEngine(TheoryDirectoryPath + TheoryName + PrologExtension)
    val input = Struct.of(TheoryName, set.toList, TermX, TermY)
    val result = engine.solve(input, TermX, TermY)
    if result.isEmpty then Option.empty
    else Option(extractPosition(result))

  /** Extract a position as the result of the prolog theory
    * @param map
    *   a map used to extract info from the prolog theory result
    * @return
    *   the position of the cell obtained by the prolog theory
    */
  private def extractPosition(map: Map[String, Term]): Position =
    val xValue: Int = map(TermX)
    val yValue: Int = map(TermY)
    (xValue, yValue)
