package Model.Cells

/** A mixin that adds a pressure switch to the cell*/
trait Switch() extends Pressable:

  abstract override def update(item: Item): Unit =
    item match
      case Item.Empty => unpressed()
      case _ =>
    super.update(item)

  /** set the state to "Not Pressed" */
  def unpressed(): Unit = _pressableState = PressableState.NotPressed
