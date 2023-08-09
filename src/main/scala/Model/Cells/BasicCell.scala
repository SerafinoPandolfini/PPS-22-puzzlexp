package Model.Cells

/** The classic, walkable cell, with no peculiar effects
  *
  * @param position
  *   the position of the cell in the room
  * @param cellItem
  *   the item on the cell
  */
class BasicCell(position: Position, cellItem: Item) extends Cell(position, cellItem):

  override def update(item: Item): BasicCell = BasicCell(position, item)
