package Model.Cells

/** A mixin that adds a pressure switch to the cell*/
trait Switch() extends Pressable:

  /** set the state to "Not Pressed" */
  def unpressed(): Unit = _pressableState = PressableState.NotPressed
