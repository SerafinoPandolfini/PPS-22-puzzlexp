package Model.Cells

import Model.Cells.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class ButtonBlockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonBlockCell: ButtonBlockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonBlockCell = ButtonBlockCell(defaultPosition)

  "A button cell" should "be openable" in {
    buttonBlockCell.walkableState should be(WalkableType.Walkable(false))
    buttonBlockCell.openBlock()
    buttonBlockCell.walkableState should be(WalkableType.Walkable(true))
  }

