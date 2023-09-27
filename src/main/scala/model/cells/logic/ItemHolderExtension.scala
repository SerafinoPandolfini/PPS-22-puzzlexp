package model.cells.logic

import model.cells.properties.Item
import model.game.ItemHolder
import utils.givens.ItemConversion.given_Conversion_Item_String

import scala.annotation.targetName

object ItemHolderExtension:
  /** extension for adding new methods for the [[ItemHolder]]
    */
  extension (itemHolder: ItemHolder)
    /** Alias for [[ItemHolder.addItems()]] */
    @targetName("addItemsAlias")
    def ++(items: List[Item]): ItemHolder = itemHolder.addItems(items)

    /** Alias for [[ItemHolder.addItem()]] */
    @targetName("addItemAlias")
    def +(items: Item): ItemHolder = itemHolder.addItem(items)

    /** Alias for [[ItemHolder.removeItem()]] */
    @targetName("removeItemAlias")
    def -(item: Item): ItemHolder = itemHolder.removeItem(item)

    /** Alias for [[ItemHolder.isPresent()]] */
    @targetName("isPresentAlias")
    def ?(item: Item): Boolean = itemHolder.isPresent(item)

    /** return a visual representation of [[ItemHolder.itemOwned]]
      * @return
      *   the string with the items in [[ItemHolder.itemOwned]]
      */
    def itemHolderToString(): String =
      "[ " + itemHolder.itemOwned.map(item => item: String).mkString(" | ") + " ]"
