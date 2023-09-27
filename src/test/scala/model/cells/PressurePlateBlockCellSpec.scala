package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.cells.properties.PressurePlateBlockGroup.*
import model.cells.properties.PressableState.*
import model.cells.properties.{Item, WalkableType}
import utils.TestUtils.*
import model.cells.logic.CellExtension.updateItem

class PressurePlateBlockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var pressurePlateBlockCell: PressurePlateBlockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    pressurePlateBlockCell = PressurePlateBlockCell(DefaultPosition, activeState = ObstacleWhenNotPressed, NotPressed)

  "A plate block cell" should "be openable" in {
    pressurePlateBlockCell.walkableState should be(WalkableType.Walkable(false))
    var cells: Set[Cell] = Set(pressurePlateBlockCell)
    val pressurePlateCell: PressurePlateCell = PressurePlateCell(Position0_1)
    cells = pressurePlateCell.updateItem(Set(pressurePlateCell, pressurePlateBlockCell), Item.Box, GenericDirection)
    pressurePlateBlockCell = cells.collect { case c: PressurePlateBlockCell => c }.head
    pressurePlateBlockCell.walkableState should be(WalkableType.Walkable(true))
  }
