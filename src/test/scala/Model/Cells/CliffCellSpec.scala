package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import TestUtils.*

class CliffCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var cliffCell: CliffCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    cliffCell = CliffCell(Item.Empty, defaultPosition, Direction.Down)


  "A cliff cell" should "have a direction" in {
    cliffCell.direction should be(Direction.Down)
  }

  "A cliff cell" should "be walkable in only a direction" in {
    cliffCell.walkableState shouldBe a[WalkableType.DirectionWalkable]
  }

