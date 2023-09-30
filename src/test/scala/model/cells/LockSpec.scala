package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.{DefaultPosition, GenericDirection}
import model.cells.logic.CellExtension.updateItem
import model.cells.properties.{Item, WalkableType}

class LockSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var lockCell: LockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    lockCell = LockCell(DefaultPosition)

  "A lock cell" should "be close" in {
    lockCell.open should be(false)
  }

  "A lock cell" should "be opened with a key" in {
    var cells: Set[Cell] = Set(lockCell)
    lockCell.open should be(false)
    cells = lockCell.updateItem(cells, Item.Axe, GenericDirection)
    lockCell = cells.collectFirst { case cell: LockCell => cell }.get
    lockCell.open should be(false)
    cells = lockCell.updateItem(cells, Item.Key, GenericDirection)
    lockCell = cells.collectFirst { case cell: LockCell => cell }.get
    lockCell.open should be(true)
  }

  "a opened lock" should "not be closed" in {
    var cells: Set[Cell] = Set(lockCell)
    cells = lockCell.updateItem(cells, Item.Key, GenericDirection)
    lockCell = cells.collectFirst { case cell: LockCell => cell }.get
    lockCell.open should be(true)
    cells = lockCell.updateItem(cells, Item.Key, GenericDirection)
    lockCell = cells.collectFirst { case cell: LockCell => cell }.get
    lockCell.open should be(true)
  }

  "only a opened lock" should "be walkable" in {
    var cells: Set[Cell] = Set(lockCell)
    lockCell.open should be(false)
    lockCell.walkableState should be(WalkableType.Walkable(false))
    cells = lockCell.updateItem(cells, Item.Key, GenericDirection)
    lockCell = cells.collectFirst { case cell: LockCell => cell }.get
    lockCell.open should be(true)
    lockCell.walkableState should be(WalkableType.Walkable(true))
  }
