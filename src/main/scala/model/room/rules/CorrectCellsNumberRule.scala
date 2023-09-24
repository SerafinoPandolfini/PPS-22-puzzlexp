package model.room.rules

import model.room.{Room, RoomImpl}
import model.cells.Cell
import model.room.rules.CorrectCellsNumberRule.{RuleMessage, Rule}
import prologEngine.PrologConverter.noProperty
import prologEngine.PrologEngine.given

/** rule for controlling if the [[Room]] have the right number of [[Cell]]s */
trait CorrectCellsNumberRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    val totalCells = RoomImpl.DefaultWidth * RoomImpl.DefaultHeight
    checkRuleValidity(RuleMessage, ruleViolations, room.cells, noProperty, Rule, totalCells)

object CorrectCellsNumberRule:

  val RuleMessage = "the number of cells is different from expected"
  val Rule = "non_repeating_cells_counter"
