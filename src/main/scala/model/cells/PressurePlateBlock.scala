package model.cells

import model.cells.properties.WalkableType.*
import model.cells.properties.{Pressable, PressurePlateBlockGroup, WalkableType, PressableState}

/** A mixin that represent a cell with a block linked to a plate */
trait PressurePlateBlock extends Cell with Pressable:
  /** the state in which the block is present */
  def activeState: PressurePlateBlockGroup

  abstract override def walkableState: WalkableType = (activeState, pressableState) match
    case (PressurePlateBlockGroup.ObstacleWhenPressed, PressableState.Pressed) |
        (PressurePlateBlockGroup.ObstacleWhenNotPressed, PressableState.NotPressed) =>
      Walkable(false)
    case _ => super.walkableState
