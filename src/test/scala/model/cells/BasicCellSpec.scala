package model.cells

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach
import model.TestUtils.*
import model.cells.logic.CellExtension.updateItem

class BasicCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var basicCell: BasicCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    basicCell = BasicCell(defaultPosition)

  "A basic cell" should "be walkable" in {
    basicCell.walkableState should be(WalkableType.Walkable(true))
  }

  "A basic cell" should "have a position" in {
    basicCell.position should be(defaultPosition)
  }

  "A basic cell" should "not be deadly" in {
    basicCell.isDeadly should not be true
  }

  "A basic cell" should "update the cell item correctly" in {
    basicCell.cellItem should be(Item.Empty)
    var cells: Set[Cell] = Set(basicCell)
    // update with new item
    cells = basicCell.updateItem(Set(basicCell), Item.Box, genericDirection)
    cells.head.cellItem should be(Item.Box)
  }
