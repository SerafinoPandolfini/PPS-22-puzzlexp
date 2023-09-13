package view

import controller.GameController

import javax.swing.*
import utils.{ColorManager, DisplayValuesManager, ImageManager}
import model.cells.Item
import java.awt.event.ActionEvent

import java.awt.{Dimension, Font}
import java.awt.event.ActionEvent
import scala.collection.immutable.List

object ToolbarElements:
  private val textFont = Font("Arial", Font.PLAIN, 16)
  private val counterText = "x0"
  private val initialScore = "0"
  val scoreText = "SCORE: "

  /** @return
    *   the button to pause the game
    */
  def createPauseButton(): JButton =
    val pauseButton = JButton(ImageIcon(ImageManager.Pause.path))
    pauseButton.setBorder(BorderFactory.createEmptyBorder())
    pauseButton.setPreferredSize(
      Dimension(DisplayValuesManager.ButtonElement.value, DisplayValuesManager.ButtonElement.value)
    )
    pauseButton.addActionListener(_ => GameController.saveGame())
    pauseButton.setBackground(ColorManager.ToolbarBackground.color)
    pauseButton

  /** @param item
    *   the item assigned to the label
    * @param iconPath
    *   the path of the image to show in the label
    * @return
    *   a label with a counter for the specified item
    */
  def createItemCounter(item: Item, iconPath: String): ItemCounterLabel =
    val itemIcon = ImageIcon(iconPath)
    val itemCounterLabel = JLabel(itemIcon)
    itemCounterLabel.setText(counterText)
    itemCounterLabel.setFont(textFont)
    itemCounterLabel.setPreferredSize(
      Dimension(DisplayValuesManager.MediumElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    itemCounterLabel.setForeground(ColorManager.ToolbarText.color)
    ItemCounterLabel(itemCounterLabel, item)

  /** @param item
    *   the item assigned to the label
    * @param iconPath
    *   the path of the image to show in the label
    * @return
    *   a label for the specified item
    * @note
    *   the image is not initially shown, but it will be shown when the [[Label.amount]] will be greater than 1
    */
  def createItemLabel(item: Item, iconPath: String): ItemLabel =
    val itemLabel = JLabel()
    itemLabel.setPreferredSize(
      Dimension(DisplayValuesManager.SmallElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    ItemLabel(itemLabel, item, itemPath = iconPath)

  /** @return
    *   a label for showing the current score
    */
  def createScoreLabel(): JLabel =
    val scoreIcon = ImageIcon(ImageManager.ScoreBackground.path)
    val scoreLabel = JLabel(scoreText concat initialScore)
    scoreLabel.setIcon(scoreIcon)
    scoreLabel.setFont(textFont)
    scoreLabel.setPreferredSize(
      Dimension(DisplayValuesManager.LargeElementWidth.value, DisplayValuesManager.ToolbarElementsHeight.value)
    )
    scoreLabel.setHorizontalTextPosition(SwingConstants.CENTER)
    scoreLabel.setVerticalTextPosition(SwingConstants.CENTER)
    scoreLabel

  /** @return
    *   the standard [[Label]]s for the toolbar
    */
  def generateStandardToolbarElements(): List[Label] =
    List(
      createItemCounter(Item.Coin, ImageManager.Coin.path),
      createItemCounter(Item.Bag, ImageManager.Bag.path),
      createItemCounter(Item.Trunk, ImageManager.Trunk.path),
      createItemCounter(Item.Key, ImageManager.Key.path),
      createItemLabel(Item.Axe, ImageManager.Axe.path),
      createItemLabel(Item.Pick, ImageManager.Pick.path)
    )
