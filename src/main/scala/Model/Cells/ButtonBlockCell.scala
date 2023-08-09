package Model.Cells

/** A cell with a block linked to a button
  * @param position
  *   the position of the cell in the room
  * @param color
  *   the color of the block
  */
class ButtonBlockCell(
    position: Position,
    cellItem: Item = Item.Empty,
    val color: Color,
    val pressableState: PressableState = PressableState.NotPressed
) extends Cell(position, cellItem)
    with ButtonBlock
    with Colorable:
  /** Open the block */
  def openBlock(): ButtonBlockCell = ButtonBlockCell(position, color = color, PressableState.Pressed)

  override def update(item: Item): ButtonBlockCell = ButtonBlockCell(position, item, color, pressableState)
