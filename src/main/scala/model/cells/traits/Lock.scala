package model.cells.traits

import model.cells.Cell
import model.cells.properties.WalkableType
import model.cells.properties.WalkableType.Walkable

/** The mixin representing a door. If the door is close is not walkable. If the door is open, it becomes walkable */
trait Lock extends Cell:
  /** if the door is open */
  def open: Boolean

  /** the player can walk through the door only if it's open */
  abstract override def walkableState: WalkableType = Walkable(open)
