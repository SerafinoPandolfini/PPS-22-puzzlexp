package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import TestUtils.*

class CoveredHoleCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var coveredHoleCell: CoveredHoleCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    coveredHoleCell = CoveredHoleCell(defaultPosition)

  "A covered hole cell" should "not be deadly" in {
    coveredHoleCell.isDeadly should not be true
  }

