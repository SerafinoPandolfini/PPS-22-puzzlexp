package model.cells

import model.cells.properties.{Item, Pressable, PressableState}

/** A cell with a pressure plate
 *
  * @param position
  *   the position of the cell in the room
  */
case class PressurePlateCell(
    position: Position,
    cellItem: Item = Item.Empty,
    pressableState: PressableState = PressableState.NotPressed
) extends Cell
    with Pressable
