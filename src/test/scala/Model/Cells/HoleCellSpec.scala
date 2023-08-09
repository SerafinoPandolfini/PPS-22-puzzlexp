package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import TestUtils.*

class HoleCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var holeCell: HoleCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    holeCell = HoleCell(defaultPosition)

  "A hole cell" should "be deadly" in {
    holeCell.isDeadly should be(true)
  }

  "A hole cell" should "be fillable with a box making it not deadly" in {
    holeCell = holeCell.update(Item.Box)
    holeCell.cellItem should be(Item.Empty)
    holeCell.isDeadly should not be true
    // now the box can be placed on the cell
    holeCell = holeCell.update(Item.Box)
    holeCell.cellItem should be(Item.Box)
  }