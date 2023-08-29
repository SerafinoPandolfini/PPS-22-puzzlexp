package utils

import model.room.Room

import java.awt.Toolkit

enum DisplayValuesManager(_value: Int):
  case CELL_SIZE extends DisplayValuesManager(32)
  case TOOLBAR_HEIGHT extends DisplayValuesManager(3)
  case SCREEN_HEIGHT extends DisplayValuesManager(Toolkit.getDefaultToolkit.getScreenSize.height)
  case ROWS extends DisplayValuesManager(Room.DefaultHeight)
  case COLS extends DisplayValuesManager(Room.DefaultWidth)

  case TOOLBAR_BORDER_THICKNESS extends DisplayValuesManager(4)

  /** @return
    *   the value of the GUI parameter
    */
  val value: Int = _value