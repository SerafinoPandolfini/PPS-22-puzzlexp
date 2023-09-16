package view

import utils.ImageManager
import utils.ConstantUtils.Origin
import java.awt.BorderLayout
import javax.swing.*

/** the GUI of the controls */
class ControlsView extends JFrame:
  val controlsPanel: JLayeredPane = createPanelsStructure()
  configureFrame()

  /** create a layered panel containing all the possible panels
    *
    * @return
    *   the panel
    */
  private def createPanelsStructure(): JLayeredPane =
    val layeredPane: JLayeredPane = JLayeredPane()
    val backgroundPanel: JPanel = createBackgroundPanel()
    layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER)
    layeredPane

  /** create the controls panel containing the background image
    *
    * @return
    *   the panel
    */
  private def createBackgroundPanel(): JPanel =
    val controlsPanel: JPanel = JPanel(BorderLayout())
    val icon: ImageIcon = ImageIcon(ImageManager.Controls.path)
    val bgLabel: JLabel = JLabel(icon)
    controlsPanel.add(bgLabel, BorderLayout.CENTER)
    controlsPanel.setBounds(Origin.x, Origin.y, ControlsView.ControlsPanelSize, ControlsView.ControlsPanelSize)
    controlsPanel

  /** configure and show the [[JFrame]] */
  private def configureFrame(): Unit =
    add(controlsPanel)
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    setSize(ControlsView.ControlsGUISize, ControlsView.ControlsGUISize)
    setResizable(false)
    setVisible(true)

object ControlsView:
  val ControlsGUISize: Int = 510
  val ControlsPanelSize: Int = ControlsGUISize - 10
