package model.cells.logic

import model.cells.Item
import model.game.ItemHolder

import scala.annotation.targetName

object ItemHolderExtension:
  /** extension for adding new methods for the [[ItemHolder]]
    */
  extension (itemHolder: ItemHolder)
    /** Alias for [[ItemHolder.addItems()]] */
    @targetName("addItemsAlias")
    def ++(items: List[Item]): ItemHolder = itemHolder.addItems(items)

    /** Alias for [[ItemHolder.removeItem()]] */
    @targetName("removeItemAlias")
    def -(item: Item): ItemHolder = itemHolder.removeItem(item)

    /** Alias for [[ItemHolder.isPresent()]] */
    @targetName("isPresentAlias")
    def ?(item: Item): Boolean = itemHolder.isPresent(item)

    /** map every [[Cell]] type to a two char string
      *
      * @param item
      *   the item to map
      * @return
      *   the mapped item
      */
    def stringedItem(item: Item): String =
      item match
        case i if i == Item.Box  => "BOX"
        case i if i == Item.Axe  => "AXE"
        case i if i == Item.Key  => "KEY"
        case i if i == Item.Pick => "PICK"
        case _                   => "??"

    /** return a visual representation of [[ItemHolder.itemOwned]]
      *
      * @param mapper
      *   the mapper to transform the items in the corresponding string
      * @return
      *   the string with the items in [[ItemHolder.itemOwned]]
      */
    def itemHolderToString(mapper: Item => String): String =
      "[ " + itemHolder.itemOwned.map(item => mapper(item)).mkString(" | ") + " ]"
