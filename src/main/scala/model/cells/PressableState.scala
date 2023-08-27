package model.cells

/** Enumeration representing the possible states of pressable elements */
enum PressableState:
  case Pressed
  case NotPressed

  /** @return the opposite state */
  def toggle: PressableState = this match
    case Pressed    => NotPressed
    case NotPressed => Pressed
