package Model.Cells

import Model.Cells.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class SwitchCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var switchCell: SwitchCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    switchCell = SwitchCell(defaultPosition)

  "A switch cell" should "be pressable and unpressable" in {
    switchCell.pressableState should be(PressableState.NotPressed)
    switchCell.pressed()
    switchCell.pressableState should be(PressableState.Pressed)
    switchCell.unpressed()
    switchCell.pressableState should be(PressableState.NotPressed)
  }



