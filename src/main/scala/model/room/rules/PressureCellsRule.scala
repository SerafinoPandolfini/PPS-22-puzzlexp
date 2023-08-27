package model.room.rules

import model.room.Room
import model.room.rules.PressureCellsRule.{Rule, RuleMessage}
import prologEngine.PrologConverter.noProperty

trait PressureCellsRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    checkRuleValidity(RuleMessage, ruleViolations, room.cells, noProperty, Rule)

object PressureCellsRule:
  val RuleMessage = "the quantity of pressure plate cells and pressure plate block cells is wrong"
  val Rule = "check_pressure_cells"