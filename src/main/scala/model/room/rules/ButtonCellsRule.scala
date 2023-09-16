package model.room.rules

import model.room.rules.ButtonCellsRule.{Rule, RuleMessage}
import model.room.Room
import model.cells.{Color, ButtonCell, ButtonBlockCell}
import prologEngine.PrologConverter.addColor
import prologEngine.PrologEngine.{*, given}

/** rule for controlling the number of [[ButtonCell]] and [[ButtonBlockCell]]
  */
trait ButtonCellsRule extends BaseRoomRule:

  override def checkRoomValidity(room: Room): List[String] =
    val ruleViolations = super.checkRoomValidity(room)
    val buttonViolations = Color.values
      .map(_.toString.toLowerCase)
      .flatMap(checkRuleValidity(RuleMessage, List.empty[String], room.cells, addColor, Rule, _))
      .toList
    ruleViolations ::: buttonViolations

object ButtonCellsRule:
  val RuleMessage = "the quantity of button cells and button block cells is wrong related to color "
  val Rule = "check_button_cells"
