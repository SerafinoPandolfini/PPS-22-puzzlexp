package model.cells

import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.logic.CellExtension.updateItem
import model.cells.properties.Item

class HoleCellSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:

  var holeCell: HoleCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    holeCell = HoleCell(DefaultPosition)

  "A hole cell" should "be deadly" in {
    holeCell.isDeadly should be(true)
  }

  "A hole cell" should "be fillable with a box making it not deadly" in {
    Given("a hole cell")
    var cells: Set[Cell] = Set(holeCell)
    When("a box is put in it")
    cells = holeCell.updateItem(cells, Item.Box)
    Then("it should become filled")
    holeCell = cells.collectFirst { case cell: HoleCell => cell }.get
    holeCell.cellItem should be(Item.Empty)
    And("it should be not deadly")
    holeCell.isDeadly should not be true
    When("another box is put on it")
    cells = holeCell.updateItem(cells, Item.Box)
    Then("its item should be box")
    cells.head.cellItem should be(Item.Box)
  }
