package view

import utils.ColorManager

import java.awt.{Color, Component}
import javax.swing.{JLabel, JList, ListCellRenderer}

/** this is a custom JLabel in the scrollPanel */
class CustomCellRenderer extends JLabel with ListCellRenderer[Any]:
  def getListCellRendererComponent(
      list: JList[_],
      value: Any,
      index: Int,
      isSelected: Boolean,
      cellHasFocus: Boolean
  ): Component =
    setText(value.toString)
    var background: Color = ColorManager.ScrollPane.color
    val foreground: Color = Color.WHITE
    if (isSelected)
      setOpaque(true)
      background = ColorManager.SelectedItemScrollPane.color
    setBackground(background)
    setForeground(foreground)

    this
