package Model.Cells

import WalkableType._

/** The cell representing a non walkable obstacle
  * @param position
  *   the position of the cell in the room
  */
class WallCell(position: Position) extends Cell(position, Item.Empty):

  override def walkableState: WalkableType = Walkable(false)

  override def update(item: Item): WallCell = this
