package model.room.rules

import model.room.Room
import prologEngine.PrologEngine
import alice.tuprolog.{Struct, Term}
import prologEngine.PrologEngine.{*, given}

trait BaseRoomRule:

  def checkRoomValidity(room: Room): List[String] = List.empty[String]

  protected def checkRuleValidity(ruleMessage: String, violations: List[String], theory: String, terms: Term*): List[String] =
    val engine = PrologEngine("/prologTheory/" + theory + ".pl")
    val input = Struct.of(theory, terms.toArray)
    println(input)
    if engine.solve(input) then violations
    else ruleMessage :: violations


