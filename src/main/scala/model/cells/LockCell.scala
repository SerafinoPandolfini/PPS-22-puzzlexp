package model.cells

import model.cells.properties.Item
import model.cells.traits.Lock

case class LockCell(position: Position, cellItem: Item = Item.Empty, open: Boolean = false) extends Cell with Lock
