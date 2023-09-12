package view

import utils.{ColorManager, DisplayValuesManager, ImageManager}

import java.awt.*
import javax.swing.{BorderFactory, ImageIcon, JButton, JComponent, JFrame, JLabel, JPanel, SwingConstants}
import java.io.File
import javax.imageio.ImageIO

object EndGamePanel:
  private val imagePanel = ImagePanel(
    ImageIO.read(File("src/main/resources/img/endGame.png"))
  )

  /** provide a base [[JPanel]] with a [[FlowLayout]] to serve as a end game panel
    *
    * @return
    *   a base for the toolbarPanel
    */
  def createEndGamePanel(): JPanel =
    val endGamePanel = JPanel(BorderLayout())
    imagePanel.setLayout(BorderLayout(0, 230))

    val homeButton = JButton()
    homeButton.setOpaque(false)
    homeButton.setContentAreaFilled(false)
    homeButton.setBorderPainted(false)
    homeButton.setIcon(ImageIcon("src/main/resources/img/home.png"))
    homeButton.setPreferredSize(
      Dimension(102, 102)
    )
    imagePanel.add(homeButton, BorderLayout.LINE_START)

    endGamePanel.add(imagePanel, BorderLayout.CENTER)
    endGamePanel.setOpaque(true)
    endGamePanel

  def createLabel(playerScore: String, totalScore: String, percentage: String): Unit =
    val label = JLabel(
      "<html><div style='text-align: center;'>CONGRATULATIONS!<br>Your score is " + playerScore + "/" + totalScore + " points <br> You completed " + percentage + "% of the map!</div></html>",
      SwingConstants.CENTER
    )
    label.setForeground(ColorManager.ToolbarText.color)
    label.setPreferredSize(
      Dimension(
        DisplayValuesManager.Cols.value * DisplayValuesManager.CellSize.value,
        DisplayValuesManager.CellSize.value * DisplayValuesManager.ToolbarHeight.value * 2
      )
    )

    label.setFont(Font("Arial", Font.PLAIN, 43))

    imagePanel.add(label, BorderLayout.SOUTH)
