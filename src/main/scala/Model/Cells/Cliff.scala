package Model.Cells

import WalkableType.*

/** A mixin for cells which can be traveled only in a fixed way */
trait Cliff extends Cell:
  /** the direction in which the cliff can be travelled */
  def direction : Direction

  abstract override def walkableState: WalkableType = DirectionWalkable(_ != direction.opposite)