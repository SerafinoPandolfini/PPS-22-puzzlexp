package Model.Cells

/** A mixin for cells which can be traveled only in a fixed way */
trait Cliff(val direction: Direction) extends Cell:
  abstract override def walkableState: WalkableType = WalkableType.DirectionWalkable(_ != direction.opposite)
