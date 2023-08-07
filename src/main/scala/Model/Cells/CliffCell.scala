package Model.Cells

/** A cell that can be walked only in a specific direction
 * @param item the item on the cell by default
 * @param position the position of the cell in the room
 * @param direction the walkable direction of the cell
 */
class CliffCell(item: Item, position: Position, direction: Direction) extends BasicCell(item, position) with Cliff(direction)
