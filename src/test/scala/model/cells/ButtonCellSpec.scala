package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.TestUtils.*
import Color.*
import model.cells.extension.CellExtension.updateItem

class ButtonCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonCell: ButtonCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonCell = ButtonCell(defaultPosition, color = Blue)

  "A button cell" should "be pressable by moving a box" in {
    buttonCell.pressableState should be(PressableState.NotPressed)
    var cells: Set[Cell] = Set(buttonCell)
    // update with new item
    cells = buttonCell.updateItem(Set(buttonCell), Item.Box, genericDirection)
    buttonCell = cells.collect { case c: ButtonCell => c }.head
    buttonCell.pressableState should be(PressableState.Pressed)
  }

  "A button cell" should "have a color" in {
    buttonCell.color should be(Blue)
  }
