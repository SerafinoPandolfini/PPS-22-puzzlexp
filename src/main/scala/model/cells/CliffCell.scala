package model.cells

import model.cells.properties.{Direction, Item}
import model.cells.traits.Cliff

/** A cell that can be walked only in a specific direction
 *
  * @param position
  *   the position of the cell in the room
  * @param cellItem
  *   the item on the cell by default
  * @param direction
  *   the walkable direction of the cell
  */
case class CliffCell(position: Position, cellItem: Item, direction: Direction) extends Cell with Cliff
