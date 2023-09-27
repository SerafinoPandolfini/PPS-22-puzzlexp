package model.cells

import model.cells.properties.Item
import model.cells.traits.Lock

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param open
  *   if the lock is opened
  */
case class LockCell(position: Position, cellItem: Item = Item.Empty, open: Boolean = false) extends Cell with Lock
