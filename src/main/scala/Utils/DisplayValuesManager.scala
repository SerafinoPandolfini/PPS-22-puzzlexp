package Utils

import java.awt.Toolkit

enum DisplayValuesManager(_value: Int):
  case CELL_SIZE extends DisplayValuesManager(32)
  case TOOLBAR_HEIGHT extends DisplayValuesManager(3)
  case SCREEN_HEIGHT extends DisplayValuesManager(Toolkit.getDefaultToolkit.getScreenSize.height)
  case ROWS
      extends DisplayValuesManager(
        (DisplayValuesManager.SCREEN_HEIGHT.value * 13 / 16 / DisplayValuesManager.CELL_SIZE.value) - DisplayValuesManager.TOOLBAR_HEIGHT.value
      )
  case COLS
      extends DisplayValuesManager(
        DisplayValuesManager.SCREEN_HEIGHT.value * 13 / 16 / DisplayValuesManager.CELL_SIZE.value
      )
  case TOOLBAR_BORDER_THICKNESS extends DisplayValuesManager(4)

  /** @return
    *   the value of the GUI parameter
    */
  val value: Int = _value
