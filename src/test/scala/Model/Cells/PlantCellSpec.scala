package Model.Cells

import Model.TestUtils.{defaultPosition, genericDirection}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class PlantCellSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var plantCell: PlantCell = _
  var cutPlantCell: PlantCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    plantCell = PlantCell(defaultPosition)
    cutPlantCell = plantCell.copy(cut = true)

  "A plant cell" should "be not cut" in {
    plantCell.cut should be(false)
  }

  "only a cut plant" should "be walkable" in {
    plantCell.cut should be(false)
    plantCell.walkableState should be(WalkableType.Walkable(false))
    cutPlantCell.cut should be(true)
    cutPlantCell.walkableState should be(WalkableType.Walkable(true))
  }
