package Model.Cells

/** The classic, walkable cell, with no peculiar effects
 *
 * @param position the position of the cell in the room
 * @param _item the item on the cell by default
 * */
class BasicCell(_item: Item, position: Position) extends Cell(position):
  cellItem = _item

  override def walkableState: WalkableType = WalkableType.Walkable(true)

  override def update(item: Item): Unit = cellItem = item
