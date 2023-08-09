package Model.Cells

/** A cell that can teleport items and the player in another cell of the room
  *
  * @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  */
class TeleportCell(position: Position, cellItem: Item = Item.Empty) extends Cell(position, cellItem):
  override def update(item: Item): TeleportCell = TeleportCell(position, item)
