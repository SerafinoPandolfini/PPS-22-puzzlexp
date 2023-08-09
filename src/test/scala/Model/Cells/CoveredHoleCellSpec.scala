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

  "A covered hole cell" should "be deadly after the player walks on it the first time" in {
    // simulate the player walking on it
    coveredHoleCell = coveredHoleCell.brokeCover()
    coveredHoleCell.isDeadly should be(true)
  }

