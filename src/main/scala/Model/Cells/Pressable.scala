package Model.Cells

/** A mixin representing a cell that can be pressed */
trait Pressable extends Cell:
  protected[this] var _pressableState: PressableState = PressableState.NotPressed
  
  /** Getter for pressableState */
  def pressableState: PressableState = _pressableState

  /** Set the state to "Pressed" */
  def pressed(): Unit = _pressableState = PressableState.Pressed
