package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import Model.Cells.Logic.CellExtension.*
import Utils.TestUtils.*

class CoveredHoleCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var coveredHoleCell: CoveredHoleCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    coveredHoleCell = CoveredHoleCell(defaultPosition)

  "A covered hole cell" should "not be deadly" in {
    coveredHoleCell.isDeadly should not be true
  }

  "A covered hole cell" should "be fillable with a box breaking the cover and making it not deadly" in {
    var cells: Set[Cell] = Set(coveredHoleCell)
    cells = coveredHoleCell.updateItem(cells, Item.Box, genericDirection)
    coveredHoleCell = cells.collectFirst { case cell: CoveredHoleCell => cell }.get
    coveredHoleCell.cellItem should be(Item.Empty)
    coveredHoleCell.isDeadly should not be true
    coveredHoleCell.cover should not be true
    // now the box can be placed on the cell
    cells = coveredHoleCell.updateItem(cells, Item.Box, genericDirection)
    cells.head.cellItem should be(Item.Box)
  }

  "A covered hole cell" should "have a broken when leaved for the first time" in {
    var cells: Set[Cell] = Set(coveredHoleCell)
    cells = coveredHoleCell.moveOut(cells)
    coveredHoleCell = cells.collectFirst { case cell: CoveredHoleCell => cell }.get
    coveredHoleCell.cellItem should be(Item.Empty)
    coveredHoleCell.isDeadly should be(true)
    coveredHoleCell.cover should not be true
  }
