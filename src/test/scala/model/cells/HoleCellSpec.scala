package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.TestUtils.*
import model.cells.extension.CellExtension.updateItem

class HoleCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var holeCell: HoleCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    holeCell = HoleCell(defaultPosition)

  "A hole cell" should "be deadly" in {
    holeCell.isDeadly should be(true)
  }

  "A hole cell" should "be fillable with a box making it not deadly" in {
    var cells: Set[Cell] = Set(holeCell)
    cells = holeCell.updateItem(cells, Item.Box, genericDirection)
    holeCell = cells.collectFirst { case cell: HoleCell => cell }.get
    holeCell.cellItem should be(Item.Empty)
    holeCell.isDeadly should not be true
    // now the box can be placed on the cell
    cells = holeCell.updateItem(cells, Item.Box, genericDirection)
    cells.head.cellItem should be(Item.Box)
  }
