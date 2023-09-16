package view

import javax.swing.{JPanel, BorderFactory}
import java.awt.*
import utils.{ColorManager, ConstantUtils, ImageManager}

object ToolbarPanel:

  private val borders = 2
  private val ToolbarBorderThickness = 4
  private val ToolbarHeight = 3

  /** provide a base [[JPanel]] with a [[FlowLayout]] and a border to serve as a toolbar panel
    *
    * @return
    *   a base for the toolbarPanel
    */
  def createBaseToolbarPanel(): JPanel =
    val toolbarPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
    toolbarPanel.setBackground(ColorManager.ToolbarBackground)
    toolbarPanel.setOpaque(true)
    toolbarPanel.setPreferredSize(
      Dimension(
        ConstantUtils.Cols * ConstantUtils.CellSize,
        ConstantUtils.CellSize * ToolbarHeight
          + borders * ToolbarBorderThickness
      )
    )
    val border = BorderFactory.createLineBorder(
      ColorManager.ToolbarBorder,
      ToolbarBorderThickness
    )
    toolbarPanel.setBorder(border)
    toolbarPanel
