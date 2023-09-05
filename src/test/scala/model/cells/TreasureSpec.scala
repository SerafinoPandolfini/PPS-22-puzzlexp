package model.cells

import model.cells.logic.TreasureExtension.*
import model.cells.logic.CellExtension.*
import model.game.ItemHolder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach
import utils.TestUtils.*

class TreasureSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var treasure: BasicCell = _
  var itemHolder: ItemHolder = _
  var scoreCounter: ScoreCounter = _

  override def beforeEach(): Unit =
    super.beforeEach()
    itemHolder = ItemHolder(List())
    scoreCounter = ScoreCounter()
    treasure = BasicCell(defaultPosition)

  "A cell with a treasure" should "be walkable" in {
    treasure.walkableState should be(WalkableType.Walkable(true))
  }

  "The size of a treasure" should "be related to a score" in {
    var cells: Set[Cell] = Set(treasure)
    cells = treasure.updateItem(cells, Item.Key, genericDirection)
    cells.head.cellItem.mapItemToValue should be(0)
    cells = treasure.updateItem(cells, Item.Coin, genericDirection)
    cells.head.cellItem.mapItemToValue should be(10)
    cells = treasure.updateItem(cells, Item.Bag, genericDirection)
    cells.head.cellItem.mapItemToValue should be(20)
    cells = treasure.updateItem(cells, Item.Trunk, genericDirection)
    cells.head.cellItem.mapItemToValue should be(50)

  }

  "The treasure" should "be placed in the item holder" in {
    var cells: Set[Cell] = Set(treasure)
    cells = treasure.updateItem(cells, Item.Coin, genericDirection)
    itemHolder.itemOwned should be(List())
    val (updatedCells, _, _, updatedItemHolder) = treasure.moveOnTreasure(cells, itemHolder, scoreCounter)
    updatedItemHolder.itemOwned should be(List(Item.Coin))
    updatedCells.head.cellItem should be(Item.Empty)
  }

  "The money" should "increase the score counter" in {
    var cells: Set[Cell] = Set(treasure)
    cells = treasure.updateItem(cells, Item.Bag, genericDirection)
    scoreCounter.score should be(0)
    val (_, _, updatedScoreCounter, _) = treasure.moveOnTreasure(cells, itemHolder, scoreCounter)
    updatedScoreCounter.score should be(20)
  }
