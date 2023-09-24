package view.game

import controller.game.GameController
import model.cells.properties.Item
import utils.constants.{ColorManager, ImageManager}
import view.game.labels.{ItemCounterLabel, ItemLabel, Label}
import view.game.ViewUpdater.pause
import java.awt.event.ActionEvent
import java.awt.{Dimension, Font}
import java.net.URL
import javax.swing.*

object ToolbarElements:
  private val textFont = Font("Arial", Font.PLAIN, 16)
  private val counterText = "x0"
  private val initialScore = "0"
  private val ToolbarElementsHeight = 96
  private val ButtonElement = 88
  private val MediumElementWidth = 96
  private val SmallElementWidth = 64
  private val LargeElementWidth = 192
  val scoreText = "SCORE: "

  /** @return
    *   the button to pause the game
    */
  def createPauseButton(): JButton =
    val pauseButton = JButton(ImageIcon(ImageManager.Pause.path))
    pauseButton.setBorder(BorderFactory.createEmptyBorder())
    pauseButton.setPreferredSize(
      Dimension(ButtonElement, ButtonElement)
    )
    pauseButton.addActionListener(_ => GameController.view.pause())
    pauseButton.setBackground(ColorManager.ToolbarBackground)
    pauseButton

  /** @param item
    *   the item assigned to the label
    * @param iconPath
    *   the path of the image to show in the label
    * @return
    *   a label with a counter for the specified item
    */
  def createItemCounter(item: Item, iconPath: URL): ItemCounterLabel =
    val itemCounterLabel = JLabel()
    itemCounterLabel.setIcon(ImageIcon(iconPath))
    itemCounterLabel.setText(counterText)
    itemCounterLabel.setFont(textFont)
    itemCounterLabel.setPreferredSize(
      Dimension(MediumElementWidth, ToolbarElementsHeight)
    )
    itemCounterLabel.setForeground(ColorManager.ToolbarText)
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
  def createItemLabel(item: Item, iconPath: URL): ItemLabel =
    val itemLabel = JLabel()
    itemLabel.setPreferredSize(
      Dimension(SmallElementWidth, ToolbarElementsHeight)
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
      Dimension(LargeElementWidth, ToolbarElementsHeight)
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
