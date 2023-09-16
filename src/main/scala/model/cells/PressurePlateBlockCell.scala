package model.cells

import model.cells.properties.{Item, PressableState, PressurePlateBlockGroup}

/** A cell that represent a cell with a block linked to a plate
 *
  * @param position
  *   the position of the cell in the room
  * @param cellItem
  *   the item in the cell
  * @param activeState
  *   the initial state of the block
  * @param pressableState
  *   the state of the corresponding pressable element
  */
case class PressurePlateBlockCell(
    position: Position,
    cellItem: Item = Item.Empty,
    activeState: PressurePlateBlockGroup,
    pressableState: PressableState = PressableState.NotPressed
) extends Cell
    with PressurePlateBlock
