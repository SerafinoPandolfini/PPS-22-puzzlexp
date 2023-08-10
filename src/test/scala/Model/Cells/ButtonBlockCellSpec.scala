package Model.Cells

import Model.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import Color.*

class ButtonBlockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonBlockCell: ButtonBlockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonBlockCell = ButtonBlockCell(defaultPosition, color = Blue)

  "A button cell" should "be openable" in {
    buttonBlockCell.walkableState should be(WalkableType.Walkable(false))
    buttonBlockCell = buttonBlockCell.openBlock()
    buttonBlockCell.walkableState should be(WalkableType.Walkable(true))
  }

  "A button cell" should "have a color" in {
    buttonBlockCell.color should be(Blue)
  }
