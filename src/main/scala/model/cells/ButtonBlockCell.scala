package model.cells

/** A cell with a block linked to a button
  * @param position
  *   the position of the cell in the room
  * @param color
  *   the color of the block
  */
case class ButtonBlockCell(
    position: Position,
    cellItem: Item = Item.Empty,
    color: Color,
    pressableState: PressableState = PressableState.NotPressed
) extends Cell
    with ButtonBlock
    with Colorable
