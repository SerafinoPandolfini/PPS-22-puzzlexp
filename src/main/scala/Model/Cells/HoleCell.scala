package Model.Cells

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param filled
  *   if the hole is filled
  */
class HoleCell(position: Position, cellItem: Item = Item.Empty, val filled: Boolean = false)
    extends Cell(position, cellItem)
    with Hole:

  override def update(item: Item): HoleCell = item match
    case Item.Box =>
      if filled then HoleCell(position, item, filled)
      else HoleCell(position, filled = true)
    case _ => HoleCell(position, filled = filled)
