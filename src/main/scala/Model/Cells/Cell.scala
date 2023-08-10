package Model.Cells

import WalkableType._

/** Type that represent the position of an element */
type Position = (Int, Int)

/** The basic element that compose a room
  * @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  */
abstract class Cell:

  def position: Position
  def cellItem: Item

  /** @return the walking state of the cell */
  def walkableState: WalkableType = Walkable(true)

  /** @return if the cell is deadly */
  def isDeadly: Boolean = false

  /** Changes the item on the cell
    * @param item
    *   the item placed on the cell
    * @return
    *   the new cell with the updated item
    */
  def update(item: Item): Cell
