package menu

import utils.ConstantUtils.*
import utils.{ColorManager, ImageManager, TransparentButton}
import java.awt.event.ActionEvent
import java.awt.geom.RoundRectangle2D
import java.awt.{BorderLayout, Color, Dimension, FlowLayout, Font, Graphics, Graphics2D, Shape}
import javax.swing.*
import scala.language.postfixOps

/** the GUI of the menu */
class MenuView extends JFrame:
  val startPanel: JLayeredPane = createStartPanel()
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
    val playButton: JButton = TransparentButton(PlayText)
    controlsButton.addActionListener((_: ActionEvent) => JOptionPane.showMessageDialog(this, "Button clicked"))
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
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setSize(MenuGUIWidth, MenuGUIHeight)
    setResizable(false)
    setVisible(true)
