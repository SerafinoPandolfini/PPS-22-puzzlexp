package view.game.pause

import controller.game.GameController
import utils.constants.ImageManager
import view.ImagePanel
import view.game.ViewUpdater.backToGame
import model.gameMap.MinimapElement
import view.game.pause.PauseExtension.popolateMap
import view.game.pause.PauseGamePanel.*
import java.awt.event.ActionListener
import java.awt.{BorderLayout, Dimension}
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.*

class PauseGamePanel(val list: List[MinimapElement], val width: Int, val height: Int):
  private val panelImage = ImagePanel(ImageIO.read(ImageManager.PauseBackground.path))
  private val saveIcon = ImageIcon(ImageManager.Save.path)
  private val playIcon = ImageIcon(ImageManager.Play.path)
  private[pause] val roomIcon = ImageIcon(ImageManager.Room.path)
  val notScaledRoomPanelWidth: Int =
    roomIcon.getIconWidth + (ImageIcon(ImageManager.LinkHorizontal.path).getIconWidth * Quantity)
  val notScaledRoomPanelHeight: Int =
    roomIcon.getIconHeight + (ImageIcon(ImageManager.LinkHorizontal.path).getIconWidth * Quantity)
  val cols: Int = list.map(e => e.position._1).max + 1
  val rows: Int = list.map(e => e.position._2).max + 1
  val scale: Double = scaling(
    width - Padding * Quantity,
    notScaledRoomPanelWidth * cols,
    height - Padding * Quantity - ButtonDimension,
    notScaledRoomPanelHeight * rows
  )
  private[pause] val linkWidth = (ImageIcon(ImageManager.LinkHorizontal.path).getIconWidth * scale).toInt
  private[pause] val roomWidth = (roomIcon.getIconWidth * scale).toInt
  private[pause] val mapWidth = (roomWidth + (linkWidth * Quantity)) * cols

  /** provide a basic [[JPanel]] to serve as a end game panel
    * @return
    *   the endGamePanel without label
    */
  def createPauseGamePanel(): JPanel =
    val endGamePanel = JPanel(BorderLayout())
    panelImage.setLayout(BorderLayout())
    val backButton = createButton(playIcon, _ => GameController.backToGame())
    val saveButton = createButton(saveIcon, _ => GameController.saveGame())
    panelImage.add(saveButton, BorderLayout.LINE_END)
    panelImage.add(backButton, BorderLayout.LINE_START)
    val mapPanel = this.popolateMap()
    mapPanel.setOpaque(false)
    panelImage.setBorder(
      EmptyBorder(
        Padding,
        Padding + (width - Padding - mapWidth) / Quantity,
        Padding,
        Padding + (width - Padding - mapWidth) / Quantity
      )
    )
    panelImage.add(mapPanel, BorderLayout.SOUTH)
    endGamePanel.add(panelImage, BorderLayout.CENTER)
    endGamePanel.setOpaque(true)
    endGamePanel

  /** create a button with a defined graphic
    * @param icon
    *   the icon to tup in the button
    * @param listener
    *   the action to perform when the button is pressed
    * @return
    *   a [[JButton]]
    */
  private def createButton(icon: ImageIcon, listener: ActionListener): JButton =
    val button = JButton()
    button.setOpaque(false)
    button.setContentAreaFilled(false)
    button.setBorderPainted(false)
    button.setIcon(icon)
    button.setPreferredSize(Dimension(ButtonDimension, ButtonDimension))
    button.addActionListener(listener)
    button

  /** find the scaling factor for an element
    * @param xtot
    *   the width to fill
    * @param x
    *   the width of the element to scale
    * @param ytot
    *   the height to fill
    * @param y
    *   the height of the element to scale
    * @return
    *   the scaling factor as a [[Double]]
    */
  private def scaling(xtot: Double, x: Double, ytot: Double, y: Double): Double =
    val scalingWidth = if xtot < x then xtot / x else NoScale
    val scalingHeight = if ytot < y then ytot / y else NoScale
    Math.min(scalingWidth, scalingHeight)

object PauseGamePanel:
  val ButtonDimension = 88
  val Padding = 9
  val NoScale = 1.0
  val Quantity = 2
