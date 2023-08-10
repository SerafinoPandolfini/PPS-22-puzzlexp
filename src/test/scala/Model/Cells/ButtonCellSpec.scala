package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import Model.TestUtils.*
import Color.*

class ButtonCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonCell: ButtonCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonCell = ButtonCell(defaultPosition, color = Blue)

  "A button cell" should "be pressable" in {
    buttonCell.pressableState should be(PressableState.NotPressed)
    buttonCell = buttonCell.pressed()
    buttonCell.pressableState should be(PressableState.Pressed)
  }

  "A button cell" should "be pressable by moving a box" in {
    buttonCell.pressableState should be(PressableState.NotPressed)
    buttonCell = buttonCell.update(Item.Box)
    buttonCell.pressableState should be(PressableState.Pressed)
  }

  "A button cell" should "have a color" in {
    buttonCell.color should be(Blue)
  }
