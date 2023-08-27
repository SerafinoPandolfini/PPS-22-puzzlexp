package model.cells

import WalkableType.*

/** A mixin representing a cell with a block linked to a button */
trait ButtonBlock extends Cell with Pressable:

  abstract override def walkableState: WalkableType = pressableState match
    case PressableState.NotPressed => Walkable(false)
    case _                         => super.walkableState
