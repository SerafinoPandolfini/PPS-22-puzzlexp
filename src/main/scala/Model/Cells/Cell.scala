package Model.Cells

import WalkableType._

/** Type that represent the position of an element */
type Position = (Int, Int)

/** The basic element that compose a room
  */
abstract class Cell:

  /** @return
    *   the cell position
    */
  def position: Position

  /** @return
    *   the cell item
    */
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
