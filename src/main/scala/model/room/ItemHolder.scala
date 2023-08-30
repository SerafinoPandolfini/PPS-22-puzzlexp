package model.room

import model.cells.Item

import scala.annotation.targetName

case class ItemHolder(itemOwned: List[Item]):
  /** check if the item is present in the item holder */
  def isPresent(item: Item): Boolean = itemOwned.contains(item)

  /** Alias for [[ItemHolder.isPresent()]] */
  @targetName("isPresentAlias")
  def ?(item: Item): Boolean = isPresent(item)

  /** add items */
  def addItems(items: List[Item]): ItemHolder = copy(itemOwned = itemOwned ::: items)

  /** Alias for [[ItemHolder.addItems()]] */
  @targetName("addItemsAlias")
  def ++(items: List[Item]): ItemHolder = addItems(items)

  /** remove an item */
  def removeItem(item: Item): ItemHolder = copy(itemOwned = for
    (element, index) <- itemOwned.zipWithIndex
    if element != item || index != itemOwned.indexOf(item)
  yield element)

  /** Alias for [[ItemHolder.removeItem()]] */
  @targetName("removeItemAlias")
  def -(item: Item): ItemHolder = removeItem(item)

  /** map every [[Cell]] type to a two char string
    *
    * @param item
    *   the item to map
    * @return
    *   the mapped item
    */
  def itemToString(item: Item): String =
    item match
      case i if i == Item.Box  => "BOX"
      case i if i == Item.Axe  => "AXE"
      case i if i == Item.Key  => "KEY"
      case i if i == Item.Pick => "PICK"
      case _                   => "??"
