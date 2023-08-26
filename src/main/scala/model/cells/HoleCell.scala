package model.cells

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param filled
  *   if the hole is filled
  */
case class HoleCell(position: Position, cellItem: Item = Item.Empty, filled: Boolean = false)
    extends Cell
    with Hole
