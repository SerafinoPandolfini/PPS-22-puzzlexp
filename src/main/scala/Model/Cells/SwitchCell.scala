package Model.Cells

/** A cell with a pressure switch
  * @param position
  *   the position of the cell in the room
  */
class SwitchCell(
    position: Position,
    cellItem: Item = Item.Empty,
    val pressableState: PressableState = PressableState.NotPressed
) extends Cell(position, cellItem)
    with Pressable:

  override def update(item: Item): SwitchCell =
    val switchState = item match
      case Item.Box => PressableState.Pressed
      case _        => PressableState.NotPressed
    SwitchCell(position, item, switchState)
