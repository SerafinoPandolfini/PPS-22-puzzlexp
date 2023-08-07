package Model.Cells

/** The type defining how a cell can be entered*/
enum WalkableType:
  /** the standard type: you can enter or not in any direction*/
  case Walkable(isWalkableTo: Boolean)
