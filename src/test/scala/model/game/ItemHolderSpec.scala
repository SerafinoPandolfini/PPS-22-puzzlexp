package model.game

import model.cells.logic.ItemHolderExtension.*
import model.cells.properties.Item
import model.game.ItemHolder
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.givens.ItemConversion.given_Conversion_Item_String

class ItemHolderSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var itemHolder: ItemHolder = _

  override def beforeEach(): Unit =
    super.beforeEach()
    itemHolder = ItemHolder(List())

  "an item holder" should "be able to add items" in {
    itemHolder.itemOwned should be(List())
    itemHolder.itemHolderToString() should be("[  ]")
    itemHolder = itemHolder.addItems(List(Item.Pick))
    itemHolder.itemOwned should be(List(Item.Pick))
    itemHolder.itemHolderToString() should be("[ PICK ]")
  }

  "an item holder" should "be able to say if an item is present or not" in {
    itemHolder.itemOwned should be(List())
    itemHolder.itemHolderToString() should be("[  ]")
    itemHolder = itemHolder.addItems(List(Item.Axe))
    itemHolder.isPresent(Item.Pick) should be(false)
    itemHolder.isPresent(Item.Axe) should be(true)
    itemHolder.itemHolderToString() should be("[ AXE ]")
  }

  "an item holder" should "be able to remove an item" in {
    itemHolder.itemOwned should be(List())
    itemHolder.itemHolderToString() should be("[  ]")
    itemHolder = itemHolder.addItems(List(Item.Axe))
    itemHolder = itemHolder.addItems(List(Item.Key))
    itemHolder.isPresent(Item.Key) should be(true)
    itemHolder = itemHolder.removeItem(Item.Key)
    itemHolder ? Item.Key should be(false)
    itemHolder.itemHolderToString() should be("[ AXE ]")
  }

  "an item holder" should "be able to show its items" in {
    (itemHolder
      + Item.Axe
      ++ List(Item.Key, Item.Pick)
      - Item.Key).itemHolderToString() should be(
      "[ AXE | PICK ]"
    )
  }
