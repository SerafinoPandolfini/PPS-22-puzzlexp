package menu

import controller.GameController
import exceptions.MapNotFoundException
import serialization.JsonDecoder
import utils.ColorManager.TransparentButtons
import utils.{ImageManager, TransparentButton}
import utils.ConstantUtils.*

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Color, Component, Dimension, FlowLayout, Font}
import javax.swing.Box.Filler
import javax.swing.{
  Box,
  BoxLayout,
  ImageIcon,
  JButton,
  JComboBox,
  JFrame,
  JLabel,
  JLayeredPane,
  JOptionPane,
  JPanel,
  WindowConstants
}

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

    /** create the map selected panel containing the comboBox, its label and the play button
      *
      * @return
      *   the panel
      */
    private def createMapFGPanel(): JPanel =
      val foreGround: JPanel = JPanel()
      val playButtonContainer: JPanel = JPanel()
      val playButton: JButton = TransparentButton(PlayText)
      val maps: Array[String] = view.mapPathAndName.map { case (_, n) => n }.toArray
      val comboBox: JComboBox[String] = JComboBox(maps)
      val selectLabel: JLabel = createSelectedLabel()
      componentConfiguration(comboBox, playButtonContainer, foreGround)
      configureListener(playButton, comboBox, selectLabel)
      playButton.setAlignmentX(Component.BOTTOM_ALIGNMENT)
      playButtonContainer.add(playButton)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(selectLabel)
      foreGround.add(computeFiller(FillerWidth, FillerHeight))
      foreGround.add(comboBox)
      foreGround.add(computeFiller(FillerWidth, FillerPlayButtonHeight))
      foreGround.add(playButtonContainer)
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

    /** configure the comboBox, the playButtonContainer and the foreGround panel */
    private def componentConfiguration(
        comboBox: JComboBox[String],
        playButtonContainer: JPanel,
        foreGround: JPanel
    ): Unit =
      comboBox.setAlignmentX(Component.CENTER_ALIGNMENT)
      comboBox.setMaximumSize(Dimension(ComboBoxWidth, ComboBoxHeight))
      playButtonContainer.setLayout(FlowLayout(FlowLayout.CENTER))
      playButtonContainer.setOpaque(false)
      foreGround.setOpaque(false)
      foreGround.setLayout(BoxLayout(foreGround, BoxLayout.Y_AXIS))
      foreGround.setBounds(Origin.x, Origin.y, MenuGUIWidth, ControlsPanelSize)

    /** this method configure the listener present in this view extension */
    private def configureListener(playButton: JButton, comboBox: JComboBox[String], selectLabel: JLabel): Unit =
      var selectedPath: String = ""
      comboBox.addActionListener(new ActionListener() {
        def actionPerformed(e: ActionEvent): Unit =
          val selectedOption = comboBox.getSelectedItem.toString
          selectLabel.setText("Selected Item: " + selectedOption)
          selectedPath = view.mapPathAndName.find { case (_, n) => n == selectedOption } match
            case Some((p, _)) => p
            case None         => throw new MapNotFoundException
      })

      playButton.addActionListener((_: ActionEvent) =>
        selectedPath match
          case "" =>
            JOptionPane.showMessageDialog(
              null,
              "Please, select a correct map"
            )
          case _ => GameController.startGame(JsonDecoder.getAbsolutePath(selectedPath))
      )
