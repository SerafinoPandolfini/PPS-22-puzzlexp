package model.cells

import model.cells.WalkableType.Walkable

/** The mixin representing a plant not walkable. It must be cut to become walkable */
trait Plant extends Cell:

  /** if the plant is cut */
  def cut: Boolean

  /** the player can walk through the plant only if it's cut */
  abstract override def walkableState: WalkableType = Walkable(cut)
