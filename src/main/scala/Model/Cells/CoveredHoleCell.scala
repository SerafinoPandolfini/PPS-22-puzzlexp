package Model.Cells

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param cover
  *   if there is a cover on the hole
  * @param filled
  *   if the hole is filled
  */
class CoveredHoleCell(
    position: Position,
    cellItem: Item = Item.Empty,
    val cover: Boolean = true,
    val filled: Boolean = false
) extends Cell(position, cellItem)
    with Hole
    with CoveredHole:

  override def update(item: Item): CoveredHoleCell = this
