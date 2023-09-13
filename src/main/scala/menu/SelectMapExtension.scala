package menu

import controller.GameController
import view.{CustomCellRenderer, CustomScrollBarUI}
import exceptions.MapNotFoundException
import serialization.JsonDecoder
import utils.ColorManager.TransparentButtons
import utils.{ColorManager, ImageManager, TransparentButton}
import utils.ConstantUtils.*

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Color, Component, Dimension, FlowLayout, Font, GridLayout}
import javax.swing.Box.Filler
import javax.swing.*
import javax.swing.JOptionPane.showMessageDialog
import scala.language.postfixOps

object SelectMapExtension:
  extension (view: MenuView)
    /** create a layered panel containing all the possible panels
      *
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
      *
      * @return
      *   the panel
      */
    private def createBackgroundPanel(): JPanel =
      val backGround = JPanel()
      val icon: ImageIcon = ImageIcon(ImageManager.SelectMapBackground.path)
      val bgLabel: JLabel = JLabel(icon)
      backGround.add(bgLabel, BorderLayout.CENTER)
      backGround.setBounds(Origin.x, Origin.y, MenuGUIWidth, MenuGUIHeight)
      backGround

    /** create the map selected panel containing the scrollPanel, its label and the play button
      *
      * @return
      *   the panel
      */
    private def createMapFGPanel(): JPanel =
      var foreGround: JPanel = JPanel()
      val listModel: DefaultListModel[String] = DefaultListModel()
      val playButtonContainer: JPanel = JPanel()
      val playButton: JButton = TransparentButton(PlayText)
      val selectLabel: JLabel = createSelectedLabel()
      view.mapPathAndName.foreach { case (_, n) => listModel.addElement(n) }
      val jList: JList[String] = JList(listModel)
      var scrollPane: JScrollPane = JScrollPane(jList)
      jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
      jList.setCellRenderer(CustomCellRenderer())
      jList.setBackground(ColorManager.ScrollPane.color)
      configureListener(playButton, jList)
      playButtonContainer.add(playButton)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(selectLabel)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(scrollPane)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(playButtonContainer)
      scrollPane = scrollPaneConfiguration(scrollPane)
      foreGround = foreGroundConfiguration(foreGround)
      playButtonContainer.setLayout(FlowLayout(FlowLayout.CENTER))
      playButtonContainer.setOpaque(false)
      foreGround

    /** create a Box.Filler with the values specify in input */
    private def computeFiller(width: Int, height: Int): Filler =
      Box.Filler(
        Dimension(width, height),
        Dimension(width, height),
        Dimension(width, height)
      )

    /** create and configure the label that will contains the selected map */
    private def createSelectedLabel(): JLabel =
      val selectLabel: JLabel = JLabel("Select a map")
      selectLabel.setFont(Font("Arial", Font.BOLD, SelectLabelFontSize))
      selectLabel.setForeground(Color.WHITE)
      selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT)
      selectLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT)
      selectLabel

    /** configure the scrollPane */
    private def scrollPaneConfiguration(
        scrollPane: JScrollPane
    ): JScrollPane =
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS)
      scrollPane.setMaximumSize(Dimension(SelectLabelSize, MenuGUIHeight))
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
      scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, ScrollBarBorderThickness))
      scrollPane.getVerticalScrollBar.setUI(CustomScrollBarUI())
      scrollPane

    /** configure the foreground panel */
    private def foreGroundConfiguration(
        foreGround: JPanel
    ): JPanel =
      foreGround.setOpaque(false)
      foreGround.setAlignmentX(Component.CENTER_ALIGNMENT)
      foreGround.setLayout(BoxLayout(foreGround, BoxLayout.Y_AXIS))
      foreGround.setBounds(Origin.x, Origin.y, MenuGUIWidth, MenuGUIHeight)
      foreGround

    /** this method configure the listener present in this view extension */
    private def configureListener(playButton: JButton, jList: JList[String]): Unit =
      playButton.addActionListener((_: ActionEvent) =>
        jList.getSelectedValue match
          case sm if sm != null =>
            view.dispose()
            GameController.startGame(
              JsonDecoder.getAbsolutePath(JsonDirectoryPath + jList.getSelectedValue + JsonExtension)
            )
          case _ => showMessageDialog(null, "Please, select a valid map")
      )
