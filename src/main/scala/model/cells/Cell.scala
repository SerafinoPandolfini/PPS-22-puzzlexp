package model.cells

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

/** Companion Object for [[Cell]] */
object Cell:
  /** given for [[Cell]] that provide a way ot order them based on position
    *
    * @return
    *   the ordering logic for [[Cell]]
    */
  given cellOrdering: Ordering[Cell] = Ordering.by(cell => (cell.position._2, cell.position._1))
