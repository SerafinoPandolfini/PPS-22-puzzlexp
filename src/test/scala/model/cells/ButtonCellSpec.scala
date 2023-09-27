package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.properties.Color.*
import model.cells.logic.CellExtension.{updateItem, moveIn}
import model.cells.properties.{PressableState, Item}

class ButtonCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonCell: ButtonCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonCell = ButtonCell(DefaultPosition, color = Blue)

  "A button cell" should "be pressable by moving a box" in {
    buttonCell.pressableState should be(PressableState.NotPressed)
    var cells: Set[Cell] = Set(buttonCell)
    cells = buttonCell.updateItem(Set(buttonCell), Item.Box, GenericDirection)
    buttonCell = cells.collect { case c: ButtonCell => c }.head
    buttonCell.pressableState should be(PressableState.Pressed)
  }

  "A button cell" should "have a color" in {
    buttonCell.color should be(Blue)
  }

  "A button cell" should "be pressable by moving on in" in {
    buttonCell.pressableState should be(PressableState.NotPressed)
    val (cells, _) = buttonCell.moveIn(Set(buttonCell))
    buttonCell = cells.collect { case c: ButtonCell => c }.head
    buttonCell.pressableState should be(PressableState.Pressed)
  }
