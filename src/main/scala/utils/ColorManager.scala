package utils
import java.awt.Color

enum ColorManager(_color: Color):
  case ToolbarBackground extends ColorManager(new Color(50, 54, 59))
  case ToolbarBorder extends ColorManager(new Color(41, 43, 46))

  /** @return
    *   the color
    */
  val color: Color = _color
