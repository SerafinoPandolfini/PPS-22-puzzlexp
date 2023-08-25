package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import Model.Cells.Logic.CellExtension.updateItem
import Utils.TestUtils.*

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
    var cells: Set[Cell] = Set(wallCell)
    cells = wallCell.updateItem(cells, Item.Box, genericDirection)
    cells.isEmpty should be(true)
  }
