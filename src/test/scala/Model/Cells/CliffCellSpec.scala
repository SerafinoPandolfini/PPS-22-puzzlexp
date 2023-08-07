package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class CliffCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var cliffCell: CliffCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    cliffCell = CliffCell(Item.Empty, (0, 0), Direction.Down)


  "A cliff cell" should "have a direction" in {
    cliffCell.direction should be(Direction.Down)
  }

  "A cliff cell" should "have a position" in {
    cliffCell.position should be (0, 0)
  }

  "A cliff cell" should "update the cell item correctly" in {
    cliffCell.cellItem should be(Item.Empty)
    cliffCell.update(Item.Box)
    cliffCell.cellItem should be(Item.Box)
  }

  "A cliff cell" should "not be deadly" in {
    cliffCell.isDeadly should not be true
  }


  "A cliff cell" should "be walkable in only a direction" in {
    cliffCell.walkableState shouldBe a[WalkableType.DirectionWalkable]
  }

