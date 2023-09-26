package model.room.rules

import model.cells.Cell
import model.room.Room
import prologEngine.PrologEngine
import alice.tuprolog.{Struct, Term}
import prologEngine.PrologConverter.convertCellToProlog
import prologEngine.PrologEngine.{*, given}
import utils.constants.PathManager.{PrologExtension, TheoryDirectoryPath}

/** the base rule for the consistency in a playable [[Room]]
  */
trait BaseRoomRule:

  /** check if the room violate any building rules
    *
    * @param room
    *   the room to validate
    * @return
    *   the list of rules violated by the room
    */
  def checkRoomValidity(room: Room): List[String] = List.empty[String]

  /** verifiy if a specific rule is violated
    *
    * @param ruleMessage
    *   the message related to the rule
    * @param violations
    *   all the previous violations
    * @param cells
    *   the cells of the analyzed room
    * @param property
    *   the property for converting cells into prolog code
    * @param theory
    *   the file name of the [[alice.tuprolog.Theory]]
    * @param terms
    *   the [[alice.tuprolog.Term]]s for the theory
    * @return
    *   the list of all violations considering this rule
    */
  private[rules] def checkRuleValidity(
      ruleMessage: String,
      violations: List[String],
      cells: Set[Cell],
      property: Cell => String,
      theory: String,
      terms: Term*
  ): List[String] =
    val engine = PrologEngine(s"$TheoryDirectoryPath$theory$PrologExtension")
    val convertedCells: Term = cells.map(convertCellToProlog(_, property))
    val input = Struct.of(theory, Array(convertedCells) ++ terms)
    if engine.solve(input) then violations
    else ruleMessage :: violations
