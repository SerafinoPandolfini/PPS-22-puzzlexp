package Model.Cells

case class PlantCell(position: Position, cellItem: Item = Item.Empty, cut: Boolean = false) extends Cell with Plant
