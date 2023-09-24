package model.room.rules

import model.room.Room
import prologEngine.PrologEngine.given
import prologEngine.PrologConverter.{noProperty, convertLinkToProlog}
import model.room.rules.BorderCellsRule.{Rule, RuleMessage}
import model.room.rules.CommonGroundTerm.{HeightLimit, WidthLimit}

/** rule related to the structure of the [[Room]] border
  */
trait BorderCellsRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    val links = room.links.map(convertLinkToProlog)
    checkRuleValidity(RuleMessage, ruleViolations, room.cells, noProperty, Rule, WidthLimit, HeightLimit, links)

object BorderCellsRule:
  val RuleMessage = "the cells on the border are not wall cell or basic cell or there is a link mismatch"
  val Rule = "check_border_cells"
