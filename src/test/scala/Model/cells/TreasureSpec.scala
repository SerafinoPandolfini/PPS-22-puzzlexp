package model.cells

import model.cells.TreasureSize.*
import model.cells.logic.TreasureExtension.*
import model.room.ItemHolder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach
import utils.TestUtils.defaultPosition

class TreasureSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var treasure: TreasureCell = _
  var itemHolder: ItemHolder = _

  override def beforeEach(): Unit =
    super.beforeEach()
    itemHolder = ItemHolder(List())
    treasure = TreasureCell(
      defaultPosition,
      List(Item.Axe, Item.Pick),
      TreasureSize.Trunk
    )

  "The size of a treasure" should "be related to a score" in {
    treasure.size.score should be(50)
    treasure.size.score should not be 20
    treasure.size.score should not be 10
  }

  "A bag treasure" should "have at most one item" in {
    val bagTreasure = TreasureCell(
      defaultPosition,
      List(Item.Axe),
      TreasureSize.Bag
    )
    bagTreasure.checkItems should be(true)
    val anotherBagTreasure = TreasureCell(
      defaultPosition,
      List(Item.Axe, Item.Key),
      TreasureSize.Bag
    )
    anotherBagTreasure.checkItems should be(false)
  }

  "A trunk treasure" should "be able to have more than one items" in {
    treasure.checkItems should be(true)
    val trunkTreasure = TreasureCell(
      defaultPosition,
      List(),
      TreasureSize.Trunk
    )
    trunkTreasure.checkItems should be(true)
  }

  "A coin treasure" should "not have items" in {
    val coinTreasure = TreasureCell(
      defaultPosition,
      List(),
      TreasureSize.Coin
    )
    coinTreasure.checkItems should be(true)
    val anotherCoinTreasure = TreasureCell(
      defaultPosition,
      List(Item.Box),
      TreasureSize.Coin
    )
    anotherCoinTreasure.checkItems should be(false)
  }

  "The items" should "be gather in the item holder" in {}

  "The money" should "increase the score counter" in {}

  "An opened treasure" should "not have items, it is only walkable" in {
    treasure.open should be(false)

  }
