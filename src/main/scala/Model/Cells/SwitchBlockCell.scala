package Model.Cells

/** A cell that represent a cell with a block linked to a switch
  * @param position
  *   the position of the cell in the room
  * @param cellItem
  *   the item in the cell
  * @param activeState
  *   the initial state of the block
  * @param pressableState
  *   the state of the corresponding pressable element
  */
class SwitchBlockCell(
    position: Position,
    cellItem: Item = Item.Empty,
    val activeState: SwitchBlockGroup,
    val pressableState: PressableState = PressableState.NotPressed
) extends Cell(position, cellItem)
    with SwitchBlock:
  /** change the state of the block (destroying eventual box) */
  def revertSwitchState(): SwitchBlockCell = SwitchBlockCell(position, activeState = activeState, pressableState.toggle)

  override def update(item: Item): SwitchBlockCell = SwitchBlockCell(position, item, activeState, pressableState)
