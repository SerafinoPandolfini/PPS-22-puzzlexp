package view.game.pause

import controller.game.GameController
import utils.constants.ImageManager
import view.ImagePanel
import view.game.ViewUpdater.back
import view.game.pause.PauseGamePanel.*
import java.awt.{BorderLayout, Dimension}
import java.awt.event.ActionListener
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.*

class PauseGamePanel():
  private val imagePanel = ImagePanel(ImageIO.read(ImageManager.PauseBackground.path))
  private val saveIcon = ImageIcon(ImageManager.Save.path)
  private val playIcon = ImageIcon(ImageManager.Play.path)

  /** provide a basic [[JPanel]] to serve as a end game panel
    * @return
    *   the endGamePanel without label
    */
  def createPauseGamePanel(): JPanel =
    val endGamePanel = JPanel(BorderLayout())
    imagePanel.setLayout(BorderLayout())

    val saveButton = createButton(saveIcon, _ => GameController.saveGame())
    val backButton = createButton(playIcon, _ => GameController.view.back(endGamePanel))

    imagePanel.add(saveButton, BorderLayout.LINE_END)
    imagePanel.add(backButton, BorderLayout.LINE_START)

    imagePanel.setBorder(EmptyBorder(padding, padding, padding, padding))

    endGamePanel.add(imagePanel, BorderLayout.CENTER)
    endGamePanel.setOpaque(true)
    endGamePanel

  private def createButton(icon: ImageIcon, listener: ActionListener): JButton =
    val button = JButton()
    button.setOpaque(false)
    button.setContentAreaFilled(false)
    button.setBorderPainted(false)
    button.setIcon(icon)
    button.setPreferredSize(Dimension(buttonDimension, buttonDimension))
    button.addActionListener(listener)
    button

object PauseGamePanel:
  private val buttonDimension = 88
  private val padding = 9
