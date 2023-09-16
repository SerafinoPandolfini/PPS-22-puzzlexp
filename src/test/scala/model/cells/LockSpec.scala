package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.{defaultPosition, genericDirection}
import model.cells.logic.CellExtension.updateItem
import model.cells.properties.{Item, WalkableType}

class LockSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var doorCell: LockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    doorCell = LockCell(defaultPosition)

  "A door cell" should "be close" in {
    doorCell.open should be(false)
  }

  "A door cell" should "be opened with a key" in {
    var cells: Set[Cell] = Set(doorCell)
    doorCell.open should be(false)
    cells = doorCell.updateItem(cells, Item.Axe, genericDirection)
    doorCell = cells.collectFirst { case cell: LockCell => cell }.get
    doorCell.open should be(false)
    cells = doorCell.updateItem(cells, Item.Key, genericDirection)
    doorCell = cells.collectFirst { case cell: LockCell => cell }.get
    doorCell.open should be(true)
  }

  "a opened door" should "not be closed" in {
    var cells: Set[Cell] = Set(doorCell)
    cells = doorCell.updateItem(cells, Item.Key, genericDirection)
    doorCell = cells.collectFirst { case cell: LockCell => cell }.get
    doorCell.open should be(true)
    cells = doorCell.updateItem(cells, Item.Key, genericDirection)
    doorCell = cells.collectFirst { case cell: LockCell => cell }.get
    doorCell.open should be(true)
  }

  "only a opened door" should "be walkable" in {
    var cells: Set[Cell] = Set(doorCell)
    doorCell.open should be(false)
    doorCell.walkableState should be(WalkableType.Walkable(false))
    cells = doorCell.updateItem(cells, Item.Key, genericDirection)
    doorCell = cells.collectFirst { case cell: LockCell => cell }.get
    doorCell.open should be(true)
    doorCell.walkableState should be(WalkableType.Walkable(true))
  }
