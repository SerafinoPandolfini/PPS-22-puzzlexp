package model.room.rules

import model.room.Room
import model.cells.Cell
import model.room.rules.CorrectCellsNumberRule.{RuleMessage, Rule}
import prologEngine.PrologEngine.{*, given}
import prologEngine.PrologConverter.*

/** rule for controlling if the [[Room]] have the right number of [[Cell]]s */
trait CorrectCellsNumberRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    val totalCells = Room.DefaultWidth * Room.DefaultHeight
    checkRuleValidity(RuleMessage, ruleViolations, room.cells, noProperty, Rule, totalCells)

object CorrectCellsNumberRule:

  val RuleMessage = "the number of cells is different from expected"
  val Rule = "non_repeating_cells_counter"
