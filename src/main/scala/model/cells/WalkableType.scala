package model.cells

/** The type defining how a cell can be entered */
enum WalkableType:
  /** the standard type: you can enter or not in any direction */
  case Walkable(isWalkableTo: Boolean)

  /** you can enter in all the directions except the opposite of the specified one */
  case DirectionWalkable(isWalkableTo: Direction => Boolean)
