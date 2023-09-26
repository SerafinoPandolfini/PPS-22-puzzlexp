package view.menu

import java.io.File
import java.nio.file.Paths
import javax.swing.*
import java.awt.*
import utils.constants.*
import controller.game.GameController
import utils.constants.GraphicManager.{MenuGUIHeight, MenuGUIWidth, Origin, ScrollBarBorderThickness}
import utils.constants.PathManager.JsonDirectoryPath
import view.menu.{CustomCellRenderer, CustomScrollBarUI, MenuView}

import java.awt.event.ActionEvent
import javax.swing.Box.Filler
import javax.swing.event.{ListSelectionEvent, ListSelectionListener}
import scala.language.postfixOps

object ForeGroundElements:
  private val SelectLabelFontSize: Int = 30
  private val SelectLabelSize: Int = 400
  private val ContinueText: String = "CONTINUE"
  private val NewGameText: String = "NEW GAME"
  private val SavesDirectoryPath: String = Paths
    .get(System.getProperty("user.home"), "puzzlexp", "saves")
    .toString + File.separator

  /** create a Box.Filler with the values specify in input */
  private[menu] def computeFiller(width: Int, height: Int): Filler =
    Box.Filler(
      Dimension(width, height),
      Dimension(width, height),
      Dimension(width, height)
    )

  /** create and configure the label that will contains the selected map */
  private[menu] def createSelectedLabel(): JLabel =
    val selectLabel: JLabel = JLabel("Select a map")
    selectLabel.setFont(Font("Arial", Font.BOLD, SelectLabelFontSize))
    selectLabel.setForeground(Color.WHITE)
    selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT)
    selectLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT)
    selectLabel

  /** configure the scrollPane */
  private[menu] def scrollPaneConfiguration(
      scrollPane: JScrollPane
  ): JScrollPane =
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS)
    scrollPane.setMaximumSize(Dimension(SelectLabelSize, MenuGUIHeight))
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
    scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, ScrollBarBorderThickness))
    scrollPane.getVerticalScrollBar.setUI(CustomScrollBarUI())
    scrollPane

  /** configure the foreground panel */
  private[menu] def foreGroundConfiguration(
      foreGround: JPanel
  ): JPanel =
    foreGround.setOpaque(false)
    foreGround.setAlignmentX(Component.CENTER_ALIGNMENT)
    foreGround.setLayout(BoxLayout(foreGround, BoxLayout.Y_AXIS))
    foreGround.setBounds(
      Origin.x,
      Origin.y,
      MenuGUIWidth,
      MenuGUIHeight
    )
    foreGround

  /** it creates the button container with the two buttons and the list model for the scrollPane */
  private[menu] def configureListModelAndButtonContainer(view: MenuView): (JPanel, JList[String]) =
    val buttonContainer: JPanel = JPanel()
    val newGameButton: JButton = TransparentButton(NewGameText)
    var continueButton: JButton = TransparentButton(ContinueText)
    continueButton.setEnabled(false)
    newGameButton.setEnabled(false)
    val jList: JList[String] = configureListModel(view)
    val listSelectionListener: ListSelectionListener = (e: ListSelectionEvent) =>
      if (!e.getValueIsAdjusting)
        continueButton = handleSelectedValue(jList, continueButton, SavesDirectoryPath, view)
        newGameButton.setEnabled(view.controller.isInternalFilePresent(view.mapPathAndName(jList.getSelectedValue)))
    jList.addListSelectionListener(listSelectionListener)
    configureListener(newGameButton, jList, JsonDirectoryPath, view)
    configureListener(continueButton, jList, SavesDirectoryPath, view)
    buttonContainer.setLayout(FlowLayout(FlowLayout.CENTER))
    buttonContainer.setOpaque(false)
    buttonContainer.add(newGameButton)
    buttonContainer.add(continueButton)
    (buttonContainer, jList)

  /** it configures a lister that starts the game */
  private def configureListener(
      button: JButton,
      jList: JList[String],
      directoryPath: String,
      view: MenuView
  ): Unit =
    button.addActionListener((_: ActionEvent) =>
      view.dispose()
      GameController.startGame(directoryPath + view.mapPathAndName(jList.getSelectedValue))
    )

  /** Enable the button if there's the file of the map */
  private def handleSelectedValue(
      jList: JList[String],
      button: JButton,
      directoryPath: String,
      view: MenuView
  ): JButton =
    if (
      view.controller.isFilePresent(
        directoryPath + view.mapPathAndName(jList.getSelectedValue)
      )
    )
      button.setEnabled(true)
    else button.setEnabled(false)
    button

  /** configures the list model */
  private def configureListModel(view: MenuView): JList[String] =
    val listModel: DefaultListModel[String] = DefaultListModel()
    view.mapPathAndName.foreach { case (n, _) => listModel.addElement(n) }
    val jList: JList[String] = JList(listModel)
    jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
    jList.setCellRenderer(CustomCellRenderer())
    jList.setBackground(ColorManager.ScrollPane)
    jList
