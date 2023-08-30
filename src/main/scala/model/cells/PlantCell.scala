package model.cells

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param cut
  *   if the plant is cut
  */
case class PlantCell(position: Position, cellItem: Item = Item.Empty, cut: Boolean = false) extends Cell with Plant
