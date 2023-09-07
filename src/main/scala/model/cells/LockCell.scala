package model.cells

case class LockCell(position: Position, cellItem: Item = Item.Empty, open: Boolean = false) extends Cell with Lock
