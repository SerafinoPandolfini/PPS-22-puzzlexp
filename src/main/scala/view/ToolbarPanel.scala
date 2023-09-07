package view

import javax.swing.{JPanel, BorderFactory}
import java.awt.*
import utils.{ColorManager, DisplayValuesManager, ImageManager}

object ToolbarPanel:
  private val borders = 2

  /** provide a base [[JPanel]] with a [[FlowLayout]] and a border to serve as a toolbar panel
   * 
   * @return a base for the toolbarPanel
   */
  def createBaseToolbarPanel(): JPanel =
    val toolbarPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
    toolbarPanel.setBackground(ColorManager.ToolbarBackground.color)
    toolbarPanel.setOpaque(true)
    toolbarPanel.setPreferredSize(
      Dimension(
        DisplayValuesManager.Cols.value * DisplayValuesManager.CellSize.value,
        DisplayValuesManager.CellSize.value * DisplayValuesManager.ToolbarHeight.value
          + borders * DisplayValuesManager.ToolbarBorderThickness.value
      )
    )
    val border = BorderFactory.createLineBorder(
      ColorManager.ToolbarBorder.color,
      DisplayValuesManager.ToolbarBorderThickness.value
    )
    toolbarPanel.setBorder(border)
    toolbarPanel
