package model.room.rules

import model.room.Room
import model.room.rules.TeleportCellsRule.{Rule, RuleMessage}
import prologEngine.PrologConverter.noProperty

trait TeleportCellsRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    checkRuleValidity(RuleMessage, ruleViolations, room.cells, noProperty, Rule)
    
object TeleportCellsRule:
  val RuleMessage = "there is a teleport or teleport destination without the other or more than 1 per type"
  val Rule = "check_teleport_cells"  
