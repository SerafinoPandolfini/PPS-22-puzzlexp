package model.cells.properties

import model.cells.properties.PressableState

/** Enumeration representing the possible states of pressable elements */
enum PressableState:
  case Pressed
  case NotPressed

  /** @return the opposite state */
  def toggle: PressableState = this match
    case Pressed    => NotPressed
    case NotPressed => Pressed
