package Model.Cells

/** A mixin representing a cell with a block linked to a button */
trait ButtonBlock() extends Cell:
  private[this] var buttonState: PressableState = PressableState.NotPressed

  abstract override def walkableState: WalkableType = buttonState match
    case PressableState.NotPressed => WalkableType.Walkable(false)
    case _ => super.walkableState

  /** Open the block */
  def openBlock(): Unit = buttonState = PressableState.Pressed


