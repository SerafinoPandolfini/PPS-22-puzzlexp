package utils
import java.awt.Color

enum ColorManager(_color: Color):
  case ToolbarBackground extends ColorManager(Color(50, 54, 59))
  case ToolbarBorder extends ColorManager(Color(41, 43, 46))
  case TransparentButtons extends ColorManager(Color(0, 0, 0, 100))

  /** @return
    *   the color
    */
  val color: Color = _color
