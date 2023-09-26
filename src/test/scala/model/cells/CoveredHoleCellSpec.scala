package model.cells

import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.logic.CellExtension.*
import model.cells.properties.Item

class CoveredHoleCellSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:

  var coveredHoleCell: CoveredHoleCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    coveredHoleCell = CoveredHoleCell(defaultPosition)

  "A covered hole cell" should "not be deadly" in {
    coveredHoleCell.isDeadly should not be true
  }

  "A covered hole cell" should "be fillable with a box breaking the cover and making it not deadly" in {
    Given("a not filled covered hole cell")
    var cells: Set[Cell] = Set(coveredHoleCell)
    When("a box is put in it")
    cells = coveredHoleCell.updateItem(cells, Item.Box)
    Then("its item should be empty")
    coveredHoleCell = cells.collectFirst { case cell: CoveredHoleCell => cell }.get
    coveredHoleCell.cellItem should be(Item.Empty)
    And("it should not be deadly")
    coveredHoleCell.isDeadly should not be true
    And("it should not be covered")
    coveredHoleCell.cover should not be true
    When("another box is put on it")
    cells = coveredHoleCell.updateItem(cells, Item.Box)
    Then("its item should become box")
    cells.head.cellItem should be(Item.Box)
  }

  "A covered hole cell" should "be broken when leaved for the first time" in {
    Given("a not filled covered hole cell")
    var cells: Set[Cell] = Set(coveredHoleCell)
    When(("the player moves out of it"))
    cells = coveredHoleCell.moveOut(cells)
    Then("the cover of the cell is broken")
    coveredHoleCell = cells.collectFirst { case cell: CoveredHoleCell => cell }.get
    coveredHoleCell.cover should not be true
    coveredHoleCell.cellItem should be(Item.Empty)
    coveredHoleCell.isDeadly should be(true)
  }
