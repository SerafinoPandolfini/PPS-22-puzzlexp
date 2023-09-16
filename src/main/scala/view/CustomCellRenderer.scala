package view

import utils.ColorManager
import utils.ConstantUtils.ScrollBarBorderThickness

import java.awt.{Color, Component, Dimension}
import javax.swing.{BorderFactory, JButton, JLabel, JList, ListCellRenderer}

/** this is a custom JLabel in the scrollPanel */
class CustomCellRenderer extends JButton with ListCellRenderer[Any]:
  def getListCellRendererComponent(
      list: JList[_],
      value: Any,
      index: Int,
      isSelected: Boolean,
      cellHasFocus: Boolean
  ): Component =
    setText(value.toString)
    var background: Color = ColorManager.ScrollPane
    val foreground: Color = Color.WHITE
    if (isSelected)
      setOpaque(true)
      background = ColorManager.SelectedItemScrollPane
    setBackground(background)
    setForeground(foreground)
    setBorder(BorderFactory.createLineBorder(ColorManager.ScrollPane, ScrollBarBorderThickness))
    setPreferredSize(Dimension(getPreferredSize.width, CustomCellRenderer.SelectCellHeight))
    this

object CustomCellRenderer:
  val SelectCellHeight: Int = 20
