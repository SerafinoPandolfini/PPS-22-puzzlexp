package Model.Cells

/** The classic, walkable cell, with no peculiar effects
 * @param position the position of the cell in the room
 * */
class BasicCell(position: Position) extends Cell(position):

  override def walkableState: WalkableType = WalkableType.Walkable(true)


