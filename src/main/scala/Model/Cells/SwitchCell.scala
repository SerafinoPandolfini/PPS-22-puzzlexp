package Model.Cells

/** A cell with a pressure switch
  * @param position
  *   the position of the cell in the room
  */
case class SwitchCell(
    position: Position,
    cellItem: Item = Item.Empty,
    pressableState: PressableState = PressableState.NotPressed
) extends Cell
    with Pressable:

  override def update(item: Item): SwitchCell =
    val switchState = item match
      case Item.Box => PressableState.Pressed
      case _        => PressableState.NotPressed
    SwitchCell(position, item, switchState)
