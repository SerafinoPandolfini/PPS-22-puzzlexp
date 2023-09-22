package view.game

import controller.game.GameController
import utils.constants.{ColorManager, GraphicManager, ImageManager}
import controller.menu.MenuController

import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*
import view.ImagePanel

import javax.swing.border.*

object EndGamePanel:

  private val labelFont = Font("Arial", Font.PLAIN, 43)
  private val homeButtonDimension = 64
  private val padding = 9
  private val borderLayoutGap = BorderLayout(0, 250)
  private val imagePanel = ImagePanel(ImageIO.read(ImageManager.End.path))
  private val homeIcon = ImageIcon(ImageManager.Home.path)
  private val labelHeight = 6

  /** provide a basic [[JPanel]] to serve as a end game panel
    * @return
    *   the endGamePanel without label
    */
  def createEndGamePanel(): JPanel =
    val endGamePanel = JPanel(BorderLayout())
    imagePanel.setLayout(borderLayoutGap)
    imagePanel.setBorder(EmptyBorder(padding, padding, padding, padding))
    val homeButton = createHomeButton()
    imagePanel.add(homeButton, BorderLayout.LINE_START)
    endGamePanel.add(imagePanel, BorderLayout.CENTER)
    endGamePanel.setOpaque(true)
    endGamePanel

  /** create the endgame label with a message containing info about the player score
    * @param playerScore
    *   the score the player did
    * @param totalScore
    *   the total score of the map
    * @param percentage
    *   the percentage of map completed
    */
  def createLabel(playerScore: String, totalScore: String, percentage: String): Unit =
    val label = JLabel(
      "<html><div style='text-align: center;'>CONGRATULATIONS!<br>Your score is " +
        playerScore + "/" + totalScore + " points <br> You completed " +
        percentage + "% of the map!</div></html>",
      SwingConstants.CENTER
    )
    label.setForeground(ColorManager.ToolbarText)
    label.setPreferredSize(
      Dimension(GraphicManager.Cols * GraphicManager.CellSize, GraphicManager.CellSize * labelHeight)
    )
    label.setFont(labelFont)
    imagePanel.add(label, BorderLayout.SOUTH)

  private def createHomeButton(): JButton =
    val homeButton = JButton()
    homeButton.setOpaque(false)
    homeButton.setContentAreaFilled(false)
    homeButton.setBorderPainted(false)
    homeButton.setIcon(homeIcon)
    homeButton.setPreferredSize(Dimension(homeButtonDimension, homeButtonDimension))
    homeButton.addActionListener(_ => {
      GameController.view.dispose()
      MenuController().start()
    })
    homeButton
