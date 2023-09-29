package model.cells

import model.cells.properties.{Color, Item, Pressable, PressableState}
import model.cells.traits.Colorable

/** A cell with a pressable button
 *
  * @param position
  *   the position of the cell in the room
  * @param cellItem
  *   the iem on the cell
  * @param color
  *   the color of the button
  * @param pressableState
  *   the pressable state of the element
  */
case class ButtonCell(
    position: Position,
    cellItem: Item = Item.Empty,
    color: Color,
    pressableState: PressableState = PressableState.NotPressed
) extends Cell
    with Pressable
    with Colorable
