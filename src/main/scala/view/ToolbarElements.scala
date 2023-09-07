package view

import javax.swing.*

import utils.{ColorManager, DisplayValuesManager, ImageManager}
import model.cells.Item
import java.awt.{Dimension, Font}
import scala.collection.immutable.List

object ToolbarElements:
  private val TextFont = Font("Arial", Font.PLAIN, 16)
  def createPauseButton(): JButton =
    val pauseButton = JButton(ImageIcon(ImageManager.Pause.path))
    pauseButton.setBorder(BorderFactory.createEmptyBorder())
    pauseButton.setPreferredSize(
      Dimension(DisplayValuesManager.ButtonElement.value, DisplayValuesManager.ButtonElement.value)
    )
    pauseButton.setBackground(ColorManager.ToolbarBackground.color)
    pauseButton

  def createItemCounter(item: Item, iconPath: String): ItemCounterLabel =
    val itemIcon = ImageIcon(iconPath)
    val itemCounterLabel = JLabel(itemIcon)
    itemCounterLabel.setText("x0")
    itemCounterLabel.setFont(TextFont)
    itemCounterLabel.setPreferredSize(
      Dimension(DisplayValuesManager.MediumElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    itemCounterLabel.setForeground(ColorManager.ToolbarText.color)
    ItemCounterLabel(itemCounterLabel, item)

  def createItemLabel(item: Item, iconPath: String): ItemLabel =
    val itemLabel = JLabel()
    itemLabel.setPreferredSize(
      Dimension(DisplayValuesManager.SmallElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    ItemLabel(itemLabel, item, itemPath = iconPath)

  def createScoreLabel(): JLabel =
    val scoreIcon = ImageIcon(ImageManager.ScoreBackground.path)
    val scoreLabel = JLabel("SCORE: 0")
    scoreLabel.setIcon(scoreIcon)
    scoreLabel.setFont(TextFont)
    scoreLabel.setPreferredSize(
      Dimension(DisplayValuesManager.LargeElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    scoreLabel.setHorizontalTextPosition(SwingConstants.CENTER)
    scoreLabel.setVerticalTextPosition(SwingConstants.CENTER)
    scoreLabel

  def generateStandardToolbarElements(): List[Label] =
    List(
      createItemCounter(Item.Coin, ImageManager.Coin.path),
      createItemCounter(Item.Bag, ImageManager.Bag.path),
      createItemCounter(Item.Trunk, ImageManager.Trunk.path),
      createItemCounter(Item.Key, ImageManager.Key.path),
      createItemLabel(Item.Axe, ImageManager.Axe.path),
      createItemLabel(Item.Pick, ImageManager.Pick.path)
    )


