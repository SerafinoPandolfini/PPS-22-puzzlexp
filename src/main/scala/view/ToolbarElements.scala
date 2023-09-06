package view

import javax.swing._
import java.awt._
import utils.ImageManager

object ToolbarElements:
  def createPauseButton(): JButton =
    val pauseButton = JButton("Pause")
    pauseButton.setFont(Font("Arial", Font.PLAIN, 16))
    pauseButton.setBorder(BorderFactory.createEmptyBorder())
    pauseButton.setForeground(Color.WHITE)
    pauseButton.setPreferredSize(Dimension(32*3, 32*3))
    pauseButton

  def createItemCounter(iconPath: String): JLabel =
    val itemIcon = ImageIcon(iconPath)
    val itemLabel = JLabel(itemIcon)
    itemLabel.setText("xN")
    itemLabel.setFont(Font("Arial", Font.PLAIN, 16))
    itemLabel.setPreferredSize(Dimension(32*3, 32*3))
    itemLabel.setForeground(Color.WHITE)
    itemLabel

  def createEmptyLabel(dimension: Dimension): JLabel =
    val emptyLabel = JLabel()
    emptyLabel.setPreferredSize(dimension)
    emptyLabel
  
  def createScoreLabel(): JLabel =
    val scoreLabel = JLabel("SCORE: 0")
    scoreLabel.setFont(Font("Arial", Font.PLAIN, 16))
    scoreLabel.setPreferredSize(Dimension(32*7, 32*3))
    scoreLabel.setForeground(Color.WHITE)
    scoreLabel

