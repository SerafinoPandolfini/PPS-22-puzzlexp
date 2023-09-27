package model.cells.logic

import model.cells.*
import prologEngine.PrologEngine
import prologEngine.PrologEngine.{*, given}
import alice.tuprolog.{Struct, Term}
import model.cells.properties.Color
import prologEngine.PrologConverter.*
import utils.constants.PathManager.{PrologExtension, TheoryDirectoryPath}

object ButtonBlockFinder:
  val TermX = "X"
  val TermY = "Y"
  val TheoryName = "search_button_block"

  /** Find the button block cells of the specified color from the specified set
    * @param cells
    *   the set of cells to fìlter
    * @param color
    *   the color of the button block cells to obtain
    * @return
    *   a set of the positions of the button block cells of the right color
    */
  def positionToRevert(cells: Set[Cell], color: Color): Set[Position] =
    val set = cells.map(convertCellToProlog(_, addColor))
    val engine = PrologEngine(TheoryDirectoryPath + TheoryName + PrologExtension)
    val input = Struct.of(TheoryName, set.toList, TermX, TermY, color.toString.toLowerCase)
    val result = engine.solve(input, TermX, TermY)
    extractPositions(result)

  /** Extract a set of positions as the result of the prolog theory
    * @param map
    *   a map used to extract info from the prolog theory result
    * @return
    *   a set composed by the positions of the cells obtained by the prolog theory
    */
  private def extractPositions(map: Map[String, Term]): Set[Position] =
    val xValues: List[Int] = map(TermX)
    val yValues: List[Int] = map(TermY)
    xValues.zip(yValues).toSet
