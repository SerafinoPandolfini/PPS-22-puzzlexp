package Model.Cells

/** A cell with a pressable button
  * @param position
  *   the position of the cell in the room
  * @param cellItem
  *   the iem on the cell
  * @param color
  *   the color of the button
  * @param pressableState
  *   the pressable state of the element
  */
class ButtonCell(
    position: Position,
    cellItem: Item = Item.Empty,
    val color: Color,
    val pressableState: PressableState = PressableState.NotPressed
) extends Cell(position, cellItem)
    with Pressable
    with Colorable:

  override def update(item: Item): ButtonCell =
    item match
      case Item.Box => pressed(item)
      case _        => ButtonCell(position, item, color, pressableState)

  /** Set the state to "Pressed" */
  def pressed(item: Item = Item.Empty): ButtonCell =
    ButtonCell(position, item, color = color, pressableState = PressableState.Pressed)
