package Model.Cells

import Model.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class SwitchCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var switchCell: SwitchCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    switchCell = SwitchCell(defaultPosition)

  "A switch cell" should "be pressable and unpressable by moving a box" in {
    switchCell.pressableState should be(PressableState.NotPressed)
    switchCell = switchCell.update(Item.Box)
    switchCell.pressableState should be(PressableState.Pressed)
    switchCell = switchCell.update(Item.Empty)
    switchCell.pressableState should be(PressableState.NotPressed)
  }
