package model.room.rules

import model.room.Room
import model.room.rules.CorrectCellsNumberRule.{RuleMessage, Rule}
import prologEngine.PrologEngine.{*, given}
import prologEngine.PrologConverter.*

trait CorrectCellsNumberRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    val convertedCells = room.cells.map(convert(_))
    val totalCells = Room.DefaultWidth * Room.DefaultHeight
    checkRuleValidity(RuleMessage, ruleViolations, Rule, convertedCells, totalCells)

object CorrectCellsNumberRule:

  val RuleMessage = "the number of cells is different from expected"
  val Rule = "non_repeating_cells_counter"
