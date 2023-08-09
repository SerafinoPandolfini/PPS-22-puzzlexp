package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import TestUtils.*

class TeleportCellsSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var cells: Set[Cell] = _

  override def beforeEach(): Unit =
    super.beforeEach()
    cells = Set(
      BasicCell(defaultPosition, Item.Empty),
      TeleportCell(defaultPosition),
      TeleportDestinationCell(defaultPosition)
    )

  "teleport cell and teleport destination cell" should "have the same behaviour of basicCell" in {
    for
      cell <- cells
      updatedCell = cell.update(Item.Box)
    yield
      cell.isDeadly should not be true
      cell.cellItem should be(Item.Empty)
      updatedCell.cellItem should be(Item.Box)
  }
