package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.TestUtils.*
import model.cells.logic.CellExtension.updateItem

class TeleportDestinationCellsSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var cells: Set[Cell] = _

  override def beforeEach(): Unit =
    super.beforeEach()
    cells = Set(
      BasicCell(defaultPosition),
      TeleportDestinationCell(defaultPosition)
    )

  "teleport destination cell" should "have the same behaviour of basicCell" in {
    for
      cell <- cells
      updatedCells = cell.updateItem(cells, Item.Box, genericDirection)
    yield
      cell.isDeadly should not be true
      cell.walkableState should be(WalkableType.Walkable(true))
      updatedCells.head.cellItem should be(Item.Box)
  }
