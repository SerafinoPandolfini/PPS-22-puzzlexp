package view.menu

import serialization.JsonDecoder
import controller.game.GameController
import utils.constants.ColorManager.TransparentButtons
import utils.constants.GraphicManager.{MenuGUIHeight, MenuGUIWidth, Origin}
import utils.constants.PathManager.JsonDirectoryPath
import utils.constants.{ColorManager, ImageManager}
import view.menu.{CustomCellRenderer, CustomScrollBarUI, MenuView}

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.*
import java.io.File
import java.nio.file.Paths
import javax.swing.{ImageIcon, JLabel, JLayeredPane, JList, JPanel, JScrollPane}
import javax.swing.Box.Filler
import javax.swing.JOptionPane.showMessageDialog
import javax.swing.event.{ListSelectionEvent, ListSelectionListener}
import scala.language.postfixOps
import view.menu.ForegroundElements

object SelectMapExtension:
  private val FillerWidth: Int = 0
  private val FillerHeight: Int = 40

  extension (view: MenuView)
    /** create a layered panel containing all the possible panels
      * @return
      *   the panel
      */
    def createPanelsStructure(): JLayeredPane =
      val layeredPane: JLayeredPane = JLayeredPane()
      val foregroundPanel: JPanel = createMapFGPanel()
      val backgroundPanel = createBackgroundPanel()
      layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER)
      layeredPane.add(foregroundPanel, JLayeredPane.PALETTE_LAYER)
      layeredPane

    /** create the background panel containing the background image
      * @return
      *   the panel
      */
    private def createBackgroundPanel(): JPanel =
      val backGround = JPanel()
      val icon: ImageIcon = ImageIcon(ImageManager.SelectMapBackground.path)
      val bgLabel: JLabel = JLabel(icon)
      backGround.add(bgLabel, BorderLayout.CENTER)
      backGround.setBounds(
        Origin.x,
        Origin.y,
        MenuGUIWidth,
        MenuGUIHeight
      )
      backGround

    /** create the map selected panel containing the scrollPanel, its label and the play button
      * @return
      *   the panel
      */
    private def createMapFGPanel(): JPanel =
      var foreGround: JPanel = JPanel()
      val selectLabel: JLabel = ForegroundElements.createSelectedLabel()
      val (buttonContainer: JPanel, jList: JList[String]) =
        ForegroundElements.configureListModelAndButtonContainer(view)
      var scrollPane: JScrollPane = JScrollPane(jList)
      foreGround.add(ForegroundElements.computeFiller(FillerWidth, FillerHeight))
      foreGround.add(selectLabel)
      foreGround.add(ForegroundElements.computeFiller(FillerWidth, FillerHeight))
      foreGround.add(scrollPane)
      foreGround.add(ForegroundElements.computeFiller(FillerWidth, FillerHeight))
      foreGround.add(buttonContainer)
      scrollPane = ForegroundElements.scrollPaneConfiguration(scrollPane)
      foreGround = ForegroundElements.foreGroundConfiguration(foreGround)
      foreGround
