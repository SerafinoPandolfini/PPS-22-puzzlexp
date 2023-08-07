package Model.Cells

/** A cell with a block linked to a button
 *
 * @param position the position of the cell in the room
 * @param color the color of the block
 */
class ButtonBlockCell(position: Position, color: Color) extends BasicCell(Item.Empty, position) with ButtonBlock with Colorable(color)

