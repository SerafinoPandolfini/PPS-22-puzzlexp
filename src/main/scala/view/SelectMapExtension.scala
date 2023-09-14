package view

import controller.GameController
import view.{CustomCellRenderer, CustomScrollBarUI}
import exceptions.MapNotFoundException
import serialization.JsonDecoder
import utils.ColorManager.TransparentButtons
import utils.{ColorManager, ImageManager, TransparentButton}
import utils.ConstantUtils.*

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Color, Component, Dimension, FlowLayout, Font, GridLayout}
import java.nio.file.Paths
import javax.swing.Box.Filler
import javax.swing.*
import javax.swing.JOptionPane.showMessageDialog
import javax.swing.event.ListSelectionListener
import javax.swing.event.ListSelectionEvent
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
      val selectLabel: JLabel = createSelectedLabel()
      val (buttonContainer: JPanel, jList: JList[String]) = configureListModelAndButtonContainer()
      var scrollPane: JScrollPane = JScrollPane(jList)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(selectLabel)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(scrollPane)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(buttonContainer)
      scrollPane = scrollPaneConfiguration(scrollPane)
      foreGround = foreGroundConfiguration(foreGround)
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

    /** it configures a lister that starts the game */
    private def configureListener(button: JButton, jList: JList[String], directoryPath: String): Unit =
      button.addActionListener((_: ActionEvent) =>
        view.dispose()
        GameController.startGame(
          Paths
            .get(System.getProperty("user.home"), "puzzlexp", "saves", jList.getSelectedValue + JsonExtension)
            .toString
        )
        // JsonDecoder.getAbsolutePath(directoryPath + jList.getSelectedValue + JsonExtension)
      )

    /** Enable the button if there's the file of the map */
    private def handleSelectedValue(jList: JList[String], button: JButton, directoryPath: String): JButton =
      if (
        view.controller.isFilePresent(
          Paths
            .get(System.getProperty("user.home"), "puzzlexp", "saves", jList.getSelectedValue + JsonExtension)
            .toString
        )
      ) // directoryPath + jList.getSelectedValue + JsonExtension))
        button.setEnabled(true)
      else button.setEnabled(false)
      button

    /** it creates the button container with the two buttons and the list model for the scrollPane */
    private def configureListModelAndButtonContainer(): (JPanel, JList[String]) =
      val buttonContainer: JPanel = JPanel()
      var newGameButton: JButton = TransparentButton(NewGameText)
      var continueButton: JButton = TransparentButton(ContinueText)
      continueButton.setEnabled(false)
      newGameButton.setEnabled(false)
      val jList: JList[String] = configureListModel()
      val listSelectionListener: ListSelectionListener = (e: ListSelectionEvent) =>
        if (!e.getValueIsAdjusting)
          continueButton = handleSelectedValue(jList, continueButton, SavesDirectoryPath)
          newGameButton = handleSelectedValue(jList, newGameButton, JsonDirectoryPath)
      jList.addListSelectionListener(listSelectionListener)
      configureListener(newGameButton, jList, JsonDirectoryPath)
      configureListener(continueButton, jList, SavesDirectoryPath)
      buttonContainer.setLayout(FlowLayout(FlowLayout.CENTER))
      buttonContainer.setOpaque(false)
      buttonContainer.add(newGameButton)
      buttonContainer.add(continueButton)
      (buttonContainer, jList)

    /** configures the list model */
    private def configureListModel(): JList[String] =
      val listModel: DefaultListModel[String] = DefaultListModel()
      view.mapPathAndName.foreach { case (_, n) => listModel.addElement(n) }
      val jList: JList[String] = JList(listModel)
      jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
      jList.setCellRenderer(CustomCellRenderer())
      jList.setBackground(ColorManager.ScrollPane.color)
      jList
