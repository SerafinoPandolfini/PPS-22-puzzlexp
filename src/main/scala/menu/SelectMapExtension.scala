package menu

import controller.GameController
import serialization.JsonDecoder
import utils.ColorManager.TransparentButtons
import utils.{ImageManager, TransparentButton}
import utils.ConstantUtils.*

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Color, Component, Dimension, FlowLayout, Font}
import javax.swing.{
  Box,
  BoxLayout,
  ImageIcon,
  JButton,
  JComboBox,
  JFrame,
  JLabel,
  JLayeredPane,
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
      val foreGround = JPanel()
      foreGround.setOpaque(false)
      val maps: Array[String] = Array("Option 1", "Option 2", "Option 3", "Option 4")
      val comboBox: JComboBox[String] = JComboBox(maps)
      val selectLabel: JLabel = JLabel("Select a map")
      val playButton: JButton = JButton(PlayText)
      comboBox.addActionListener(new ActionListener() {
        def actionPerformed(e: ActionEvent): Unit =
          val selectedOption = comboBox.getSelectedItem.toString
          selectLabel.setText("Selected Item: " + selectedOption)

      })
      comboBox.setAlignmentX(Component.CENTER_ALIGNMENT)
      comboBox.setMaximumSize(Dimension(ComboBoxWidth, ComboBoxHeight))
      playButton.setAlignmentX(Component.CENTER_ALIGNMENT)
      playButton.addActionListener((_: ActionEvent) =>
        GameController.startGame(JsonDecoder.getAbsolutePath("src/main/resources/json/testMap.json"))
      )

      selectLabel.setFont(Font("Arial", Font.BOLD, SelectLabelFontSize))
      selectLabel.setForeground(Color.WHITE)
      selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT)
      selectLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT)
      foreGround.setLayout(BoxLayout(foreGround, BoxLayout.Y_AXIS))
      foreGround.add(
        Box.Filler(
          Dimension(FillerWidth, FillerHeight),
          Dimension(FillerWidth, FillerHeight),
          Dimension(FillerWidth, FillerHeight)
        )
      )
      foreGround.add(selectLabel)
      foreGround.add(
        Box.Filler(
          Dimension(FillerWidth, FillerHeight),
          Dimension(FillerWidth, FillerHeight),
          Dimension(FillerWidth, FillerHeight)
        )
      )
      foreGround.add(comboBox)
      foreGround.add(Box.createVerticalGlue())
      foreGround.add(playButton)
      foreGround.setBounds(Origin.x, Origin.y, MenuGUIWidth, ControlsPanelSize)
      foreGround
