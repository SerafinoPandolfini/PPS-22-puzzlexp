package Model.Cells

import Model.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import Model.Cells.Extension.CellExtension.updateItem

class SwitchCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var switchCell: SwitchCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    switchCell = SwitchCell(defaultPosition)

  "A switch cell" should "be pressable and unpressable by moving a box" in {
    switchCell.pressableState should be(PressableState.NotPressed)
    var cells: Set[Cell] = Set(switchCell)
    // update with new item
    cells = switchCell.updateItem(Set(switchCell), Item.Box, genericDirection)
    switchCell = cells.collect { case c: SwitchCell => c }.head
    // switchCell = switchCell.update(Item.Box)
    switchCell.pressableState should be(PressableState.Pressed)
    cells = switchCell.updateItem(Set(switchCell), Item.Empty, genericDirection)
    switchCell = cells.collect { case c: SwitchCell => c }.head
    // switchCell = switchCell.update(Item.Empty)
    switchCell.pressableState should be(PressableState.NotPressed)
  }
