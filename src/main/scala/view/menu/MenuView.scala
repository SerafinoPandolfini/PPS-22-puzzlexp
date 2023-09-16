package view.menu

import controller.game.GameController
import controller.menu.MenuController
import serialization.JsonDecoder
import utils.constants.GraphicManager.{MenuGUIHeight, MenuGUIWidth, Origin, Point2D}
import utils.constants.{ColorManager, ImageManager}
import view.menu.ControlsView
import view.menu.MenuView

import java.awt.*
import java.awt.event.{ActionEvent, WindowAdapter, WindowEvent}
import java.awt.geom.RoundRectangle2D
import java.util.Locale
import javax.swing.*
import scala.collection.immutable.ListMap
import scala.language.postfixOps
import SelectMapExtension.createPanelsStructure

/** the GUI of the menu */
case class MenuView(controller: MenuController) extends JFrame:
  var startPanel: JLayeredPane = createStartPanel()
  var playButton: JButton = _
  var mapPathAndName: ListMap[String, String] = controller.searchMapFiles()
  configureFrame()

  /** create the start menu panel containing all the panels
    *
    * @return
    *   the panel
    */
  private def createStartPanel(): JLayeredPane =
    val layeredPane: JLayeredPane = JLayeredPane()
    val backgroundPanel: JPanel = createBackgroundPanel()
    val buttonPanel = createButtonsPanel()
    layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER)
    layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER)
    layeredPane

  /** create the buttons panel containing the two buttons
    *
    * @return
    *   the panel
    */
  private def createButtonsPanel(): JPanel =
    val buttonPanel: JPanel = JPanel()
    val playButtonContainer: JPanel = JPanel(FlowLayout(FlowLayout.CENTER))
    val controlsButtonContainer: JPanel = JPanel(FlowLayout(FlowLayout.CENTER))
    buttonPanel.setOpaque(false)
    playButtonContainer.setOpaque(false)
    controlsButtonContainer.setOpaque(false)
    val controlsButton: JButton = TransparentButton(MenuView.ControlsText)
    controlsButton.addActionListener((_: ActionEvent) => ControlsView())
    playButton = TransparentButton(MenuView.PlayText)
    playButton.addActionListener((_: ActionEvent) =>
      startPanel.removeAll()
      startPanel = this.createPanelsStructure()
      add(startPanel)
      revalidate()
      repaint()
    )
    playButtonContainer.add(playButton)
    controlsButtonContainer.add(controlsButton)
    buttonPanel.setLayout(BoxLayout(buttonPanel, BoxLayout.Y_AXIS))
    buttonPanel.add(playButtonContainer)
    buttonPanel.add(controlsButtonContainer)
    buttonPanel.setBounds(
      MenuView.ButtonCoordinate.x,
      MenuView.ButtonCoordinate.y,
      MenuView.ButtonsPanelWidth,
      MenuView.ButtonsPanelHeight
    )
    buttonPanel

  /** create the background panel containing the background image
    *
    * @return
    *   the panel
    */
  private def createBackgroundPanel(): JPanel =
    val startMenuPanel: JPanel = JPanel(BorderLayout())
    val icon: ImageIcon = ImageIcon(ImageManager.StartMenuBackground.path)
    val bgLabel: JLabel = JLabel(icon)
    startMenuPanel.add(bgLabel, BorderLayout.CENTER)
    startMenuPanel.setBounds(Origin.x, Origin.y, MenuGUIWidth, MenuView.BackgroundPanelHeight)
    startMenuPanel

  /** configure and show the [[JFrame]] */
  private def configureFrame(): Unit =
    add(startPanel)
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    addWindowListener(
      new WindowAdapter:
        override def windowClosing(e: WindowEvent): Unit =
          JOptionPane.showConfirmDialog(
            null,
            "Do you really want to quit?",
            "Confirm Close",
            JOptionPane.YES_NO_OPTION
          ) match
            case JOptionPane.YES_OPTION => dispose()
    )
    setSize(MenuGUIWidth, MenuGUIHeight)
    setResizable(false)
    setVisible(true)

object MenuView:
  val ControlsText: String = "CONTROLS"
  val PlayText: String = "PLAY"
  val BackgroundPanelHeight: Int = 580
  val ButtonsPanelHeight: Int = 100
  val ButtonsPanelWidth: Int = 400
  val ButtonCoordinate: Point2D = Point2D(40, 200)
