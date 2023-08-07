package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import TestUtils.*

class ButtonCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonCell: ButtonCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonCell = ButtonCell(defaultPosition)

  "A button cell" should "be pressable" in {
    buttonCell.pressableState should be(PressableState.NotPressed)
    buttonCell.pressed()
    buttonCell.pressableState should be(PressableState.Pressed)
  }

  "A button cell" should "be pressable by moving a box" in {
    buttonCell.pressableState should be(PressableState.NotPressed)
    buttonCell.update(Item.Box)
    buttonCell.pressableState should be(PressableState.Pressed)
  }