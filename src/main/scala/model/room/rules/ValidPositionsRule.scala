package model.room.rules

import model.room.Room
import model.room.rules.ValidPositionsRule.{RuleMessage, Rule}
import prologEngine.PrologEngine.{*, given}
import prologEngine.PrologConverter.*
import CommonGroundTerm.{HeightLimit, WidthLimit}

trait ValidPositionsRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    checkRuleValidity(RuleMessage, ruleViolations, room.cells, noProperty, Rule, WidthLimit, HeightLimit)

object ValidPositionsRule:

  val RuleMessage = "there are cells that are out of bounds"
  val Rule = "valid_positions"
