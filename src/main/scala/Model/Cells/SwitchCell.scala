package Model.Cells

/** A cell with a pressure switch
 * @param position the position of the cell in the room
 */
class SwitchCell(position: Position) extends BasicCell(Item.Empty, position) with Switch()
