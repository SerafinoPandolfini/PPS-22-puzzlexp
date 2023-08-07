package Model.Cells

/** A cell with a pressable button
 * @param position the position of the cell in the room
 */
class ButtonCell(position: Position) extends BasicCell(Item.Empty, position) with Pressable

