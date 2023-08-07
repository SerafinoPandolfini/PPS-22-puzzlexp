package Model.Cells

/** The classic, walkable cell, with no peculiar effects
 *
 * @param position the position of the cell in the room
 * @param item the item on the cell by default
 * */
class BasicCell(item: Item, position: Position) extends Cell(position):
  _cellItem = item

  override def walkableState: WalkableType = WalkableType.Walkable(true)

  override def update(item: Item): Unit = _cellItem = item
