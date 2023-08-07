package Model.Cells

/** A cell with a pressable button
 * @param position the position of the cell in the room
 * @param color the color of the button
 */
class ButtonCell(position: Position, color: Color) extends BasicCell(Item.Empty, position) with Pressable with Colorable(color)


