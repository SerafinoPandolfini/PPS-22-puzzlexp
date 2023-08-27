package model.cells

case class DoorCell(position: Position, cellItem: Item = Item.Empty, open: Boolean = false) extends Cell with Door
