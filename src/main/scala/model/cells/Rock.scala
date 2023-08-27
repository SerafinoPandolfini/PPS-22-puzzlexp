package model.cells

import model.cells.WalkableType.{DirectionWalkable, Walkable}

/** The mixin representing a rock not walkable. It must be broken to become walkable */
trait Rock extends Cell:
  /** if the rock is broken */
  def broken: Boolean

  /** the player can walk through the rock only if it's broken */
  abstract override def walkableState: WalkableType = Walkable(broken)
