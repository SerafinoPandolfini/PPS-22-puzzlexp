package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import TestUtils.*

class WallCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var wallCell: WallCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    wallCell = WallCell(defaultPosition)

  "A wall cell" should "not be walkable" in {
    wallCell.walkableState should be(WalkableType.Walkable(false))
  }
  
  "A wall cell" should "never have a item different from Empty" in {
    wallCell.cellItem should be(Item.Empty)
    wallCell.update(Item.Box)
    wallCell.cellItem should be(Item.Empty)
  }
  