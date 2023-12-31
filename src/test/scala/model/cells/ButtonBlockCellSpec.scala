package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.cells.properties.Color.*
import utils.TestUtils.*
import model.cells.logic.CellExtension.{moveIn, updateItem}
import model.cells.properties.{Color, WalkableType, Item}

class ButtonBlockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonBlockCell: ButtonBlockCell = _
  val color: Color = Blue

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonBlockCell = ButtonBlockCell(DefaultPosition, color = color)

  "A button cell" should "be openable moving a box on the corresponding button" in {
    buttonBlockCell.walkableState should be(WalkableType.Walkable(false))
    val buttonCell: ButtonCell = ButtonCell(Position0_1, color = color)
    val cells = buttonCell.updateItem(Set(buttonCell, buttonBlockCell), Item.Box, GenericDirection)
    buttonBlockCell = cells.collect { case c: ButtonBlockCell => c }.head
    buttonBlockCell.walkableState should be(WalkableType.Walkable(true))
  }

  "A button cell" should "have a color" in {
    buttonBlockCell.color should be(Blue)
  }

  "A button cell" should "be openable moving on the corresponding button" in {
    buttonBlockCell.walkableState should be(WalkableType.Walkable(false))
    val buttonCell: ButtonCell = ButtonCell(Position0_1, color = color)
    val (cells, _) = buttonCell.moveIn(Set(buttonCell, buttonBlockCell))
    buttonBlockCell = cells.collect { case c: ButtonBlockCell => c }.head
    buttonBlockCell.walkableState should be(WalkableType.Walkable(true))
  }
