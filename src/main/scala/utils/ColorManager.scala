package utils
import java.awt.Color

enum ColorManager(_color: Color):
  case ToolbarBackground extends ColorManager(Color(50, 54, 59))
  case TransparentButtons extends ColorManager(Color(0, 0, 0, 100))
  case ScrollPane extends ColorManager(Color(33, 33, 33))
  case SelectedItemScrollPane extends ColorManager(Color(164, 102, 14))
  case ScrollBarForeground extends ColorManager(Color(94, 94, 94))
  case ToolbarBorder extends ColorManager(new Color(61, 63, 66))
  case ToolbarText extends ColorManager(new Color(255, 255, 255))

  /** @return
    *   the color
    */
  val color: Color = _color
