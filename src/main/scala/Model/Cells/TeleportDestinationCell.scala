package Model.Cells

/** A cell where items and the player can be teleported
  *
  * @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  */
case class TeleportDestinationCell(position: Position, cellItem: Item = Item.Empty) extends Cell
