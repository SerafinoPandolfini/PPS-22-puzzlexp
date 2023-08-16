package Model.Cells

import Model.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import SwitchBlockGroup.*
import PressableState.*
import Model.Cells.Extension.CellExtension.updateItem

class SwitchBlockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var switchBlockCell: SwitchBlockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    switchBlockCell = SwitchBlockCell(defaultPosition, activeState = ObstacleWhenNotPressed, NotPressed)

  "A switch block cell" should "be openable" in {
    switchBlockCell.walkableState should be(WalkableType.Walkable(false))
    var cells: Set[Cell] = Set(switchBlockCell)
    // update with new item
    val switchCell: SwitchCell = SwitchCell((0, 1))
    cells = switchCell.updateItem(Set(switchCell, switchBlockCell), Item.Box, genericDirection)
    switchBlockCell = cells.collect { case c: SwitchBlockCell => c }.head
    // switchBlockCell = switchBlockCell.revertSwitchState()
    switchBlockCell.walkableState should be(WalkableType.Walkable(true))
  }
