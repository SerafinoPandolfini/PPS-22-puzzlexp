package model.room

import model.cells.Item

case class ItemHolder(itemOwned: List[Item]):
  /** check if the item is present in the item holder */
  def isPresent(item: Item): Boolean = itemOwned.contains(item)

  /** add an item */
  def addItem(item: Item): ItemHolder = copy(itemOwned = itemOwned :+ item)

  /** remove an item */
  def removeItem(item: Item): ItemHolder = copy(itemOwned = for
    (element, index) <- itemOwned.zipWithIndex
    if element != item || index != itemOwned.indexOf(item)
  yield element)
