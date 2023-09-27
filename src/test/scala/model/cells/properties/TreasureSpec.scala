package model.cells.properties

import model.cells.logic.CellExtension.*
import model.cells.logic.TreasureExtension.*
import model.cells.properties.{Item, WalkableType}
import model.cells.{BasicCell, Cell}
import model.game.{CurrentGame, ItemHolder}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import utils.givens.ItemConversion.given_Conversion_Item_Int

class TreasureSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var treasure: BasicCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    treasure = BasicCell(DefaultPosition)

  "A cell with a treasure" should "be walkable" in {
    treasure.walkableState should be(WalkableType.Walkable(true))
  }

  "The size of a treasure" should "be related to a score" in {
    var cells: Set[Cell] = Set(treasure)
    cells = treasure.updateItem(cells, Item.Key, GenericDirection)
    cells.head.cellItem.convert should be(0)
    cells = treasure.updateItem(cells, Item.Coin, GenericDirection)
    cells.head.cellItem.convert should be(10)
    cells = treasure.updateItem(cells, Item.Bag, GenericDirection)
    cells.head.cellItem.convert should be(20)
    cells = treasure.updateItem(cells, Item.Trunk, GenericDirection)
    cells.head.cellItem.convert should be(50)

  }

  "The treasure" should "be placed in the item holder and update the score counter" in {
    val scoreInit = CurrentGame.scoreCounter
    CurrentGame.addItem(Item.Coin)
    CurrentGame.itemHolder.itemOwned.contains(Item.Coin) should be(true)
    CurrentGame.scoreCounter should be(scoreInit + 10)
  }
