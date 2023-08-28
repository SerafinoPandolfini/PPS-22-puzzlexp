package model.room.rules

import model.room.Room
import prologEngine.PrologEngine.{*, given}
import prologEngine.PrologConverter.*
import BorderCellsRule.{Rule, RuleMessage}
import CommonGroundTerm.{HeightLimit, WidthLimit}

trait BorderCellsRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    val links = room.links.map(convertLinkToProlog(_))
    checkRuleValidity(RuleMessage, ruleViolations, room.cells, noProperty, Rule, WidthLimit, HeightLimit, links)

object BorderCellsRule:
  val RuleMessage = "the cells on the border are not wall cell or basic cell or there is a link mismatch"
  val Rule = "check_border_cells"
