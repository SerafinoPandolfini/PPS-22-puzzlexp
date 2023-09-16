package model.cells

import model.cells.properties.WalkableType.*
import model.cells.properties.{Item, WalkableType}

/** The cell representing a non walkable obstacle
  * @param position
  *   the position of the cell in the room
  */
case class WallCell(position: Position) extends Cell:

  override def cellItem: Item = Item.Empty
  override def walkableState: WalkableType = Walkable(false)
