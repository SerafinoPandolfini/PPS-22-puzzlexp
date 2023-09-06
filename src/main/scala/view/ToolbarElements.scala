package view

import javax.swing._
import java.awt._
import utils.{ImageManager, ColorManager, DisplayValuesManager}

object ToolbarElements:
  private val TextFont = Font("Arial", Font.PLAIN, 16)
  def createPauseButton(): JButton =
    val pauseButton = JButton("Pause")
    pauseButton.setFont(TextFont)
    pauseButton.setBorder(BorderFactory.createEmptyBorder())
    pauseButton.setPreferredSize(
      Dimension(DisplayValuesManager.MediumElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    pauseButton.setForeground(ColorManager.ToolbarText.color)
    pauseButton

  def createItemCounter(iconPath: String): JLabel =
    val itemIcon = ImageIcon(iconPath)
    val itemCounterLabel = JLabel(itemIcon)
    itemCounterLabel.setText("x0")
    itemCounterLabel.setFont(TextFont)
    itemCounterLabel.setPreferredSize(
      Dimension(DisplayValuesManager.MediumElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    itemCounterLabel.setForeground(ColorManager.ToolbarText.color)
    itemCounterLabel

  def createItemLabel(iconPath: String): JLabel =
    val itemIcon = ImageIcon(iconPath)
    val itemLabel = JLabel(itemIcon)
    itemLabel.setPreferredSize(
      Dimension(DisplayValuesManager.SmallElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    itemLabel

  def createScoreLabel(): JLabel =
    val scoreLabel = JLabel("SCORE: 0")
    scoreLabel.setFont(TextFont)
    scoreLabel.setPreferredSize(
      Dimension(DisplayValuesManager.LargeElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    scoreLabel.setHorizontalTextPosition(SwingConstants.LEFT)
    scoreLabel.setForeground(ColorManager.ToolbarText.color)
    scoreLabel
