package utils
import java.awt.Color

enum ColorManager(_color: Color):
  case ToolbarBackground extends ColorManager(new Color(50, 54, 59))
  case ToolbarBorder extends ColorManager(new Color(61, 63, 66))
  case ToolbarText extends ColorManager(new Color(255, 255, 255))

  /** @return
    *   the color
    */
  val color: Color = _color
