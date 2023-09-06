package model.cells

import model.cells.logic.TreasureExtension.*
import model.cells.logic.CellExtension.*
import model.game.{CurrentGame, ItemHolder}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach
import utils.TestUtils.*

class TreasureSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var treasure: BasicCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
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

  "The treasure" should "be placed in the item holder and update the score counter" in {
    val scoreInit = CurrentGame.scoreCounter
    CurrentGame.addItem(Item.Coin)
    CurrentGame.itemHolder.itemOwned.contains(Item.Coin) should be(true)
    CurrentGame.scoreCounter should be(scoreInit + 10)
  }
