package model.room.rules

import model.room.Room
import model.room.rules.ValidPositionsRule.{RuleMessage, Rule}
import prologEngine.PrologEngine.{*, given}
import prologEngine.PrologConverter.*

trait ValidPositionsRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    val convertedCells = room.cells.map(convert(_))
    val widthLimit = Room.DefaultWidth - 1
    val heightLimit = Room.DefaultHeight - 1
    checkRuleValidity(RuleMessage, ruleViolations, Rule, convertedCells, widthLimit, heightLimit)

object ValidPositionsRule:

  val RuleMessage = "there are cells that are out of bounds"
  val Rule = "valid_positions"
