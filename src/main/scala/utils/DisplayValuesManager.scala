package utils

import model.room.Room

import java.awt.Toolkit

enum DisplayValuesManager(_value: Int):
  case CellSize extends DisplayValuesManager(32)
  case ToolbarHeight extends DisplayValuesManager(3)
  case ScreenHeight extends DisplayValuesManager(Toolkit.getDefaultToolkit.getScreenSize.height)
  case Rows extends DisplayValuesManager(Room.DefaultHeight)
  case Cols extends DisplayValuesManager(Room.DefaultWidth)
  case ToolbarBorderThickness extends DisplayValuesManager(4)

  /** @return
    *   the value of the GUI parameter
    */
  val value: Int = _value
