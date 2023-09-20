package model.cells.properties

import model.cells.properties.PressableState

/** A mixin representing a cell that can be pressed */
trait Pressable:
  /** the state of the pressable element */
  def pressableState: PressableState
