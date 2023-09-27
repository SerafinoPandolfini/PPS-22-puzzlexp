package model.game

import model.cells.properties.Item

case class ItemHolder(itemOwned: List[Item]):
  /** @param item
    *   the item to check
    * @return
    *   true if the item is present in the item holder
    */
  def isPresent(item: Item): Boolean = itemOwned.contains(item)

  /** @param items
    *   the items to add
    * @return
    *   the new instance of ItemHolder with the items added
    */
  def addItems(items: List[Item]): ItemHolder = copy(itemOwned = itemOwned ::: items)

  /** @param item
    *   the item to add
    * @return
    *   the new instance of ItemHolder with the item added
    */
  def addItem(item: Item): ItemHolder = copy(itemOwned = item :: itemOwned)

  /** @param item
    *   the items to remove
    * @return
    *   the new instance of ItemHolder without the item
    */
  def removeItem(item: Item): ItemHolder = copy(itemOwned = for
    (element, index) <- itemOwned.zipWithIndex
    if element != item || index != itemOwned.indexOf(item)
  yield element)
