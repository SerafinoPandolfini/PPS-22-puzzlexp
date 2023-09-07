package menu

import controller.GameController
import serialization.JsonDecoder
import utils.ConstantUtils.*
import utils.{ColorManager, ImageManager, TransparentButton}
import SelectMapExtension.*
import java.awt.event.{ActionEvent, WindowAdapter, WindowEvent}
import java.awt.geom.RoundRectangle2D
import java.awt.{BorderLayout, Color, Dimension, FlowLayout, Font, Graphics, Graphics2D, Shape}
import java.util.Locale
import javax.swing.*
import scala.language.postfixOps

/** the GUI of the menu */
class MenuView(val continue: Boolean) extends JFrame:
  var startPanel: JLayeredPane = createStartPanel()
  var playButton: JButton = _
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
    val buttonPanel = JPanel(FlowLayout(FlowLayout.CENTER))
    buttonPanel.setOpaque(false)
    val controlsButton: JButton = TransparentButton(ControlsText)
    controlsButton.addActionListener((_: ActionEvent) => ControlsView())
    if (continue) playButton = TransparentButton(ContinueText)
    else
      playButton = TransparentButton(PlayText)
      playButton.addActionListener((_: ActionEvent) =>
        startPanel.removeAll()
        startPanel = this.createPanelsStructure()
        add(startPanel)
        revalidate()
        repaint()
      )
    buttonPanel.add(controlsButton)
    buttonPanel.add(playButton)
    buttonPanel.setBounds(ButtonCoordinate.x, ButtonCoordinate.y, ButtonsPanelWidth, ButtonsPanelHeight)
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
    startMenuPanel.setBounds(Origin.x, Origin.y, MenuGUIWidth, BackgroundPanelHeight)
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
    // setResizable(false)
    setVisible(true)
