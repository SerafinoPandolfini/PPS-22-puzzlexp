package view.game

import utils.constants.{ColorManager, GraphicManager, ImageManager}

import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*
import view.ImagePanel

object EndGamePanel:

  private val labelFont = Font("Arial", Font.PLAIN, 43)
  private val homeButtonDimension = Dimension(102, 102)
  private val borderLayoutGap = BorderLayout(0, 230)
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

    val homeButton = JButton()
    homeButton.setOpaque(false)
    homeButton.setContentAreaFilled(false)
    homeButton.setBorderPainted(false)
    homeButton.setIcon(homeIcon)
    homeButton.setPreferredSize(homeButtonDimension)
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
    label.setPreferredSize(Dimension(GraphicManager.Cols * GraphicManager.CellSize, GraphicManager.CellSize * labelHeight))

    label.setFont(labelFont)

    imagePanel.add(label, BorderLayout.SOUTH)
