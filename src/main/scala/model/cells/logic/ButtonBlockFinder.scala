package Model.Cells.Logic

import Model.Cells.*
import prologEngine.PrologEngine
import PrologEngine.{*, given}
import prologEngine.PrologConverter.*
import alice.tuprolog.{Struct, Term}

object ButtonBlockFinder:
  val termX = "X"
  val termY = "Y"

  /** Find the button block cells of the specified color from the specified set
    * @param cells
    *   the set of cells to fìlter
    * @param color
    *   the color of the button block cells to obtain
    * @return
    *   a set of the positions of the button block cells of the right color
    */
  def positionToRevert(cells: Set[Cell], color: Color): Set[Position] =
    val set = cells.map(convert(_, addColor))
    val engine = PrologEngine("../prologTheory/search_button_block.pl")
    val input = Struct.of("search_button_block", set.toList, termX, termY, color.toString.toLowerCase)
    val result = engine.solve(input, termX, termY)
    extractPositions(result)

  /** Extract a set of positions as the result of the prolog theory
    * @param map
    *   a map used to extract info from the prolog theory result
    * @return
    *   a set composed by the positions of the cells obtained by the prolog theory
    */
  private def extractPositions(map: Map[String, Term]): Set[Position] =
    val xValues: List[Int] = map(termX)
    val yValues: List[Int] = map(termY)
    xValues.zip(yValues).toSet