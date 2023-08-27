package model.room

import model.cells.Item
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
    itemHolder = itemHolder.addItem(Item.Pick)
    itemHolder.itemOwned should be(List(Item.Pick))
  }

  "an item holder" should "be able to say if an item is present or not" in {
    itemHolder.itemOwned should be(List())
    itemHolder = itemHolder.addItem(Item.Axe)
    itemHolder.isPresent(Item.Pick) should be(false)
    itemHolder.isPresent(Item.Axe) should be(true)
  }

  "an item holder" should "be able to remove an item" in {
    itemHolder.itemOwned should be(List())
    itemHolder = itemHolder.addItem(Item.Axe)
    itemHolder = itemHolder.addItem(Item.Key)
    itemHolder.isPresent(Item.Key) should be(true)
    itemHolder = itemHolder.removeItem(Item.Key)
    itemHolder.isPresent(Item.Key) should be(false)
  }
