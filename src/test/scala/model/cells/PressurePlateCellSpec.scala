package model.cells

import utils.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.cells.logic.CellExtension.updateItem
import model.cells.properties.{PressableState, Item}

class PressurePlateCellSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var pressurePlateCell: PressurePlateCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    pressurePlateCell = PressurePlateCell(defaultPosition)

  "A pressurePlate cell" should "be pressable and unpressable by moving a box" in {
    pressurePlateCell.pressableState should be(PressableState.NotPressed)
    var cells: Set[Cell] = Set(pressurePlateCell)
    cells = pressurePlateCell.updateItem(Set(pressurePlateCell), Item.Box, genericDirection)
    pressurePlateCell = cells.collect { case c: PressurePlateCell => c }.head
    pressurePlateCell.pressableState should be(PressableState.Pressed)
    cells = pressurePlateCell.updateItem(Set(pressurePlateCell), Item.Empty, genericDirection)
    pressurePlateCell = cells.collect { case c: PressurePlateCell => c }.head
    pressurePlateCell.pressableState should be(PressableState.NotPressed)
  }
