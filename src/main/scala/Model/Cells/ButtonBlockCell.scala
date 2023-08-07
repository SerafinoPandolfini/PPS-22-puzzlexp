package Model.Cells

/** A cell with a block linked to a button
 *
 * @param position the position of the cell in the room
 */
class ButtonBlockCell(position: Position) extends BasicCell(Item.Empty, position) with ButtonBlock 

