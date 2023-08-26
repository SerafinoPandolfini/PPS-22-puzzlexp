package Model.Room

import Model.Cells.Item

case class ItemHolder(itemOwned: Set[Item]):
  /** check if the item is present in the item holder */
  def isPresent(item: Item): Boolean = itemOwned.contains(item)

  /** add an item */
  def addItem(item: Item): ItemHolder = copy(itemOwned = itemOwned + item)
