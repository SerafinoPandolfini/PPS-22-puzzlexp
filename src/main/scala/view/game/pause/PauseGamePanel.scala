package view.game.pause

import controller.game.GameController
import utils.constants.ImageManager
import view.ImagePanel
import view.game.ViewUpdater.back
import model.gameMap.MinimapElement
import view.game.pause.PauseExtension.popolateMap
import java.awt.event.ActionListener
import java.awt.{BorderLayout, Dimension}
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.*

class PauseGamePanel(val list: List[MinimapElement], var width: Int, var height: Int):
  private val ButtonDimension = 88
  private val Padding = 9
  private val NoScale = 1.0
  private val PanelImage = ImagePanel(ImageIO.read(ImageManager.PauseBackground.path))
  private val SaveIcon = ImageIcon(ImageManager.Save.path)
  private val PlayIcon = ImageIcon(ImageManager.Play.path)
  private[pause] val RoomIcon = ImageIcon(ImageManager.Room.path)
  private val notScaledRoomPanelWidth =
    RoomIcon.getIconWidth + (ImageIcon(ImageManager.LinkHorizontal.path).getIconWidth * 2)
  private val notScaledRoomPanelHeight =
    RoomIcon.getIconHeight + (ImageIcon(ImageManager.LinkHorizontal.path).getIconWidth * 2)
  private[pause] val cols = list.map(e => e.position._1).max + 1
  private[pause] val rows = list.map(e => e.position._2).max + 1
  private[pause] val scale = scaling(
    width - Padding * 2,
    notScaledRoomPanelWidth * cols,
    height - Padding * 2 - ButtonDimension,
    notScaledRoomPanelHeight * rows
  )
  private[pause] val linkWidth = (ImageIcon(ImageManager.LinkHorizontal.path).getIconWidth * scale).toInt
  private[pause] val roomWidth = (RoomIcon.getIconWidth * scale).toInt
  private[pause] val mapWidth = (roomWidth + (linkWidth * 2)) * cols

  /** provide a basic [[JPanel]] to serve as a end game panel
    * @return
    *   the endGamePanel without label
    */
  def createPauseGamePanel(): JPanel =
    val endGamePanel = JPanel(BorderLayout())
    PanelImage.setLayout(BorderLayout())
    val backButton = createButton(PlayIcon, _ => GameController.view.back(endGamePanel))
    val saveButton = createButton(SaveIcon, _ => GameController.saveGame())
    PanelImage.add(saveButton, BorderLayout.LINE_END)
    PanelImage.add(backButton, BorderLayout.LINE_START)
    val mapPanel = this.popolateMap()
    mapPanel.setOpaque(false)
    PanelImage.setBorder(
      EmptyBorder(
        Padding,
        Padding + (width - Padding - mapWidth) / 2,
        Padding,
        Padding + (width - Padding - mapWidth) / 2
      )
    )
    PanelImage.add(mapPanel, BorderLayout.SOUTH)
    endGamePanel.add(PanelImage, BorderLayout.CENTER)
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
