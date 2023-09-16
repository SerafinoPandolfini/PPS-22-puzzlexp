package model.cells.traits

import model.cells.properties.{Direction, WalkableType}
import model.cells.properties.WalkableType.*
import model.cells.Cell

/** A mixin for cells which can be traveled only in a fixed way */
trait Cliff extends Cell:
  /** the direction in which the cliff can be travelled */
  def direction: Direction

  abstract override def walkableState: WalkableType = DirectionWalkable(_ != direction.opposite)
