package model.cells

import controller.game.GameController
import utils.TestUtils.{DefaultPosition, GenericDirection}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.cells.logic.UseItemExtension.*
import model.cells.logic.CellExtension.updateItem
import model.cells.properties.{WalkableType, Item}
import model.game.CurrentGame

class PlantCellSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var plantCell: PlantCell = _
  var cutPlantCell: PlantCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    plantCell = PlantCell(DefaultPosition)
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

  "a plant cell if it is not cut" should "not contain a box" in {
    var cells: Set[Cell] = Set(plantCell)
    plantCell.cut should be(false)
    cells = plantCell.updateItem(cells, Item.Box, GenericDirection)
    plantCell = cells.head match
      case cell: PlantCell => cell
    plantCell.cellItem should be(Item.Empty)
  }

  "a plant cell if it's cut" should "contain a box" in {
    var cells: Set[Cell] = Set(cutPlantCell)
    cutPlantCell.cut should be(true)
    cells = cutPlantCell.updateItem(cells, Item.Box, GenericDirection)
    cutPlantCell = cells.collectFirst { case cell: PlantCell => cell }.get
    cutPlantCell.cellItem should be(Item.Box)
  }

  "only a axe" should "be able to cut a plant" in {
    var cells: Set[Cell] = Set(plantCell)
    CurrentGame.addItem(Item.Pick)
    cells = cells.concat(plantCell.usePowerUp())
    plantCell = cells.head match
      case cell: PlantCell => cell
    plantCell.cut should be(false)
    CurrentGame.addItem(Item.Axe)
    cells = plantCell.usePowerUp()
    plantCell = cells.head match
      case cell: PlantCell => cell
    plantCell.cut should be(true)
  }
