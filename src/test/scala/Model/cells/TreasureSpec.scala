package model.cells

import model.cells.TreasureSize.*
import model.cells.logic.TreasureExtension.*
import model.game.ItemHolder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach
import utils.TestUtils.defaultPosition

class TreasureSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var treasure: TreasureCell = _
  var itemHolder: ItemHolder = _
  var scoreCounter: ScoreCounter = _

  override def beforeEach(): Unit =
    super.beforeEach()
    itemHolder = ItemHolder(List())
    scoreCounter = ScoreCounter()
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

  "The items" should "be gather in the item holder" in {
    itemHolder.itemOwned should be(List())
    val (treasureUpdated, itemHolderUpdated, _) = treasure.openTheTreasure(itemHolder, scoreCounter)
    itemHolderUpdated.itemOwned should be(List(Item.Axe, Item.Pick))
    treasureUpdated.open should be(true)
  }

  "The money" should "increase the score counter" in {
    scoreCounter.score should be(0)
    val (_, _, scoreCounterUpdated) = treasure.openTheTreasure(itemHolder, scoreCounter)
    scoreCounterUpdated.score should be(50)
  }

  "An opened treasure" should "not have items, it is only walkable" in {
    treasure.open should be(false)
    treasure.walkableState should be(WalkableType.Walkable(true))
    val (openedTreasure, _, _) = treasure.openTheTreasure(itemHolder, scoreCounter)
    openedTreasure.open should be(true)
    openedTreasure.walkableState should be(WalkableType.Walkable(true))
    openedTreasure.items should be(List())
  }
