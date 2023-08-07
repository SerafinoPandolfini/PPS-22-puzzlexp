package Model.Cells

/** The cell representing an obstacle that is walkable and deadly, but can be filled with a box item
 * @param position the position of the cell in the room */
class HoleCell(position: Position) extends BasicCell(Item.Empty, position) with Hole
