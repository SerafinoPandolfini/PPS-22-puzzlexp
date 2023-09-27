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

  private val LabelFont = Font("Arial", Font.PLAIN, 43)
  private val HomeButtonDimension = 64
  private val Padding = 9
  private val BorderLayoutGap = BorderLayout(0, 250)
  private val imagePanel = ImagePanel(ImageIO.read(ImageManager.End.path))
  private val HomeIcon = ImageIcon(ImageManager.Home.path)
  private val LabelHeight = 6

  /** provide a basic [[JPanel]] to serve as a end game panel
    * @return
    *   the endGamePanel without label
    */
  def createEndGamePanel(): JPanel =
    val endGamePanel = JPanel(BorderLayout())
    imagePanel.setLayout(BorderLayoutGap)
    imagePanel.setBorder(EmptyBorder(Padding, Padding, Padding, Padding))
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
  def createEndGameLabel(playerScore: String, totalScore: String, percentage: String): Unit =
    val label = JLabel(
      "<html><div style='text-align: center;'>CONGRATULATIONS!<br>Your score is " +
        playerScore + "/" + totalScore + " points <br> You completed " +
        percentage + "% of the map!</div></html>",
      SwingConstants.CENTER
    )
    label.setForeground(ColorManager.ToolbarText)
    label.setPreferredSize(
      Dimension(GraphicManager.Cols * GraphicManager.CellSize, GraphicManager.CellSize * LabelHeight)
    )
    label.setFont(LabelFont)
    imagePanel.add(label, BorderLayout.SOUTH)

  /** Create a button to the main menu of the game
    * @return
    *   a [[JButton]] to the home of the game
    */
  private def createHomeButton(): JButton =
    val homeButton = JButton()
    homeButton.setOpaque(false)
    homeButton.setContentAreaFilled(false)
    homeButton.setBorderPainted(false)
    homeButton.setIcon(HomeIcon)
    homeButton.setPreferredSize(Dimension(HomeButtonDimension, HomeButtonDimension))
    homeButton.addActionListener(_ => {
      GameController.endGame()
      MenuController().start()
    })
    homeButton
