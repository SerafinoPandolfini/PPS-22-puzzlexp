package model.cells

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}

import model.cells.logic.CellExtension.*
import model.cells.properties.{WalkableType, Item}
import model.game.ItemHolder
import utils.TestUtils.*

class BasicCellSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:

  var basicCell: BasicCell = _
  var itemHolder: ItemHolder = _

  override def beforeEach(): Unit =
    super.beforeEach()
    basicCell = BasicCell(defaultPosition)

  "A basic cell" should "be walkable" in {
    basicCell.walkableState should be(WalkableType.Walkable(true))
  }

  "A basic cell" should "have a position" in {
    basicCell.position should be(defaultPosition)
  }

  "A basic cell" should "not be deadly" in {
    basicCell.isDeadly should not be true
  }

  "A basic cell" should "update the cell item correctly" in {
    Given("a basicCell without item")
    Then("it should have item Empty")
    basicCell.cellItem should be(Item.Empty)
    var cells: Set[Cell] = Set(basicCell)
    When("its item is updated with Box")
    cells = basicCell.updateItem(Set(basicCell), Item.Box)
    Then(("it should have item Box"))
    cells.head.cellItem should be(Item.Box)
  }
