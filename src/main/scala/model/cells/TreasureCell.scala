package model.cells

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param open
  *   if the treasure is opened
  * @param items
  *   The items in the treasure
  * @param size
  *   The size of the treasure
  */
case class TreasureCell(
    position: Position,
    items: List[Item],
    size: TreasureSize,
    cellItem: Item = Item.Empty,
    open: Boolean = false
) extends Cell
    with Treasure
