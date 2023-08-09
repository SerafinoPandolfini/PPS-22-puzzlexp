package Model.Cells

/** A cell that can be walked only in a specific direction
 * @param position the position of the cell in the room
 * @param cellItem the item on the cell by default
 * @param direction the walkable direction of the cell
 */
class CliffCell(position: Position, cellItem: Item, val direction: Direction) extends Cell(position, cellItem) with Cliff:

  override def update(item: Item): CliffCell = CliffCell(position, item, direction)
