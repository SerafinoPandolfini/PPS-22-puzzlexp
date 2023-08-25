package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import Model.Cells.Logic.CellExtension.updateItem
import Utils.TestUtils.*

class PressurePlateCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var pressurePlateCell: PressurePlateCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    pressurePlateCell = PressurePlateCell(defaultPosition)

  "A pressurePlate cell" should "be pressable and unpressable by moving a box" in {
    pressurePlateCell.pressableState should be(PressableState.NotPressed)
    var cells: Set[Cell] = Set(pressurePlateCell)
    // update with new item
    cells = pressurePlateCell.updateItem(Set(pressurePlateCell), Item.Box, genericDirection)
    pressurePlateCell = cells.collect { case c: PressurePlateCell => c }.head
    // pressurePlateCell = pressurePlateCell.update(Item.Box)
    pressurePlateCell.pressableState should be(PressableState.Pressed)
    cells = pressurePlateCell.updateItem(Set(pressurePlateCell), Item.Empty, genericDirection)
    pressurePlateCell = cells.collect { case c: PressurePlateCell => c }.head
    // pressurePlateCell = pressurePlateCell.update(Item.Empty)
    pressurePlateCell.pressableState should be(PressableState.NotPressed)
  }
