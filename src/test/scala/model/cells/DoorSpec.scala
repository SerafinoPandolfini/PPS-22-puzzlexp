package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.TestUtils.{defaultPosition, genericDirection}
import model.cells.logic.CellExtension.updateItem

class DoorSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var doorCell: DoorCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    doorCell = DoorCell(defaultPosition)

  "A door cell" should "be close" in {
    doorCell.open should be(false)
  }

  "A door cell" should "be opened with a key" in {
    var cells: Set[Cell] = Set(doorCell)
    doorCell.open should be(false)
    cells = doorCell.updateItem(cells, Item.Axe, genericDirection)
    doorCell = cells.collectFirst { case cell: DoorCell => cell }.get
    doorCell.open should be(false)
    cells = doorCell.updateItem(cells, Item.Key, genericDirection)
    doorCell = cells.collectFirst { case cell: DoorCell => cell }.get
    doorCell.open should be(true)
  }

  "a opened door" should "not be closed" in {}

  "only a opened door" should "be walkable" in {}
