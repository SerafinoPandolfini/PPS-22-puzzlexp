package Model.Cells

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach

class BasicCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var basicCell: BasicCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    basicCell = BasicCell((0, 0))

  "A basic cell" should "be walkable" in {
    basicCell.walkableState should be(WalkableType.Walkable(true))
  }

  "A basic cell" should "have a position" in {
    basicCell.position should be (0, 0)
  }





