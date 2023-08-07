package Model.Cells

/** Type that represent the position of an element */
type Position = (Int, Int)

/** The basic element that compose a room
 * @param position The position of the cell in the room*/
abstract class Cell(val position: Position):
  
  /** @return the walking state of the cell */
  def walkableState : WalkableType


