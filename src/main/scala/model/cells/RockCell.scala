package model.cells

case class RockCell(position: Position, cellItem: Item = Item.Empty, broken: Boolean = false) extends Cell with Rock
