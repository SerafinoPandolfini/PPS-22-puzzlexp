package Model.Cells

/** The mixin representing an obstacle that is walkable and deadly, but can be filled with a box item */
trait Hole extends Cell:
  private[this] var filled = false

  abstract override def isDeadly: Boolean = true
