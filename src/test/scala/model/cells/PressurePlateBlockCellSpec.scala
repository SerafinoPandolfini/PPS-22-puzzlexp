package model.cells

import model.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import PressurePlateBlockGroup.*
import PressableState.*
import model.cells.extension.CellExtension.updateItem

class PressurePlateBlockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var pressurePlateBlockCell: PressurePlateBlockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    pressurePlateBlockCell = PressurePlateBlockCell(defaultPosition, activeState = ObstacleWhenNotPressed, NotPressed)

  "A plate block cell" should "be openable" in {
    pressurePlateBlockCell.walkableState should be(WalkableType.Walkable(false))
    var cells: Set[Cell] = Set(pressurePlateBlockCell)
    // update with new item
    val pressurePlateCell: PressurePlateCell = PressurePlateCell((0, 1))
    cells = pressurePlateCell.updateItem(Set(pressurePlateCell, pressurePlateBlockCell), Item.Box, genericDirection)
    pressurePlateBlockCell = cells.collect { case c: PressurePlateBlockCell => c }.head
    pressurePlateBlockCell.walkableState should be(WalkableType.Walkable(true))
  }
