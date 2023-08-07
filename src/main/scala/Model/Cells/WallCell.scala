package Model.Cells

/** The cell representing a non walkable obstacle
 * @param position the position of the cell in the room
 * */
class WallCell(position: Position) extends Cell(position):

  override def walkableState: WalkableType = WalkableType.Walkable(false)

  override def update(item: Item): Unit = ()
