package model.game

import model.cells.Item
import model.cells.logic.ItemHolderExtension.*
import model.game.ItemHolder
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class ItemHolderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var itemHolder: ItemHolder = _

  override def beforeEach(): Unit =
    super.beforeEach()
    itemHolder = ItemHolder(List())

  "an item holder" should "be able to add items" in {
    itemHolder.itemOwned should be(List())
    itemHolder.itemHolderToString(itemHolder.stringedItem) should be("[  ]")
    itemHolder = itemHolder.addItems(List(Item.Pick))
    itemHolder.itemOwned should be(List(Item.Pick))
    itemHolder.itemHolderToString(itemHolder.stringedItem) should be("[ PICK ]")
  }

  "an item holder" should "be able to say if an item is present or not" in {
    itemHolder.itemOwned should be(List())
    itemHolder.itemHolderToString(itemHolder.stringedItem) should be("[  ]")
    itemHolder = itemHolder.addItems(List(Item.Axe))
    itemHolder.isPresent(Item.Pick) should be(false)
    itemHolder.isPresent(Item.Axe) should be(true)
    itemHolder.itemHolderToString(itemHolder.stringedItem) should be("[ AXE ]")
  }

  "an item holder" should "be able to remove an item" in {
    itemHolder.itemOwned should be(List())
    itemHolder.itemHolderToString(itemHolder.stringedItem) should be("[  ]")
    itemHolder = itemHolder.addItems(List(Item.Axe))
    itemHolder = itemHolder.addItems(List(Item.Key))
    itemHolder.isPresent(Item.Key) should be(true)
    itemHolder = itemHolder.removeItem(Item.Key)
    itemHolder ? Item.Key should be(false)
    itemHolder.itemHolderToString(itemHolder.stringedItem) should be("[ AXE ]")
  }

  "an item holder" should "be able to show its items" in {
    itemHolder
      ++ List(Item.Axe, Item.Key, Item.Pick)
      - Item.Key itemHolderToString itemHolder.stringedItem should be(
        "[ AXE | PICK ]"
      )
  }
