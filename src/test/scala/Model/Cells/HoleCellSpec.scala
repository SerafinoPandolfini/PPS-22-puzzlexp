package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import TestUtils.*

class HoleCellSpec  extends AnyFlatSpec with BeforeAndAfterEach:
  
  var holeCell: HoleCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    holeCell = HoleCell(defaultPosition)

  "A wall cell" should "be deadly" in {
    holeCell.isDeadly should be(true)
  }

