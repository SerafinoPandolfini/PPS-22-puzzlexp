package Model.Cells

/** The mixin representing a rock not walkable. It must be broken to become walkable */
trait Rock extends Cell:
  /** if the rock is broken */
  def broken: Boolean
