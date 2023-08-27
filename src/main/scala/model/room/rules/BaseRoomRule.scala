package model.room.rules

import model.cells.Cell
import model.room.Room
import prologEngine.PrologEngine
import alice.tuprolog.{Struct, Term}
import prologEngine.PrologConverter.convertCellToProlog
import prologEngine.PrologEngine.{*, given}

trait BaseRoomRule:

  def checkRoomValidity(room: Room): List[String] = List.empty[String]

  protected def checkRuleValidity(
      ruleMessage: String,
      violations: List[String],
      cells: Set[Cell],
      property: Cell => String,
      theory: String,
      terms: Term*,
  ): List[String] =
    val engine = PrologEngine("/prologTheory/" + theory + ".pl")
    val convertedCells: Term = cells.map(convertCellToProlog(_, property))
    val input = Struct.of(theory, Array(convertedCells) ++ terms)
    println(input)
    if engine.solve(input) then violations
    else ruleMessage :: violations
