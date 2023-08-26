package model.cells

/** A mixin representing a cell that can be pressed */
trait Pressable:
  /** the state of the pressable element */
  def pressableState: PressableState
