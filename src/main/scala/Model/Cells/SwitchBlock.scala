package Model.Cells

import WalkableType.*

/** A mixin that represent a cell with a block linked to a switch */
trait SwitchBlock extends Cell with Pressable:
  /** the state in which the block is present */
  def activeState: SwitchBlockGroup

  abstract override def walkableState: WalkableType = (activeState, pressableState) match
    case (SwitchBlockGroup.ObstacleWhenOn, PressableState.Pressed) |
        (SwitchBlockGroup.ObstacleWhenOff, PressableState.NotPressed) =>
      Walkable(false)
    case _ => super.walkableState
