package view

import java.awt.*
import javax.swing.*
import scala.collection.immutable.List
import utils.{ColorManager, DisplayValuesManager, ImageManager}

import java.awt.event.{ActionEvent, ActionListener, KeyEvent}
import javax.swing.border.Border

class Game extends JFrame:
  // TODO: secondo me Game prende initial position e ha il metodo chargeRoom per caricare le stanze
  val numberOfCells: Int = DisplayValuesManager.Rows.value * DisplayValuesManager.Cols.value
  val panel = new JPanel(new GridLayout(DisplayValuesManager.Rows.value, DisplayValuesManager.Cols.value))
  val mainPanel = new JPanel(new BorderLayout())
  val toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT))
  this.add(mainPanel)
  this.toolbarPanel.setBackground(ColorManager.ToolbarBackground.color)
  this.toolbarPanel.setOpaque(true)
  this.toolbarPanel.setPreferredSize(
    new Dimension(
      DisplayValuesManager.Cols.value * DisplayValuesManager.CellSize.value,
      DisplayValuesManager.CellSize.value * DisplayValuesManager.ToolbarHeight.value
    )
  )

  val border: Border =
    BorderFactory.createLineBorder(
      ColorManager.ToolbarBorder.color,
      DisplayValuesManager.ToolbarBorderThickness.value
    )
  this.toolbarPanel.setBorder(this.border)
  val berryLabel: JLabel = new JLabel()
  this.berryLabel.setIcon(new ImageIcon(ImageManager.Berry.path))
  this.toolbarPanel.add(this.berryLabel)

  this.mainPanel.add(this.toolbarPanel, BorderLayout.NORTH)
  this.mainPanel.add(this.panel, BorderLayout.CENTER)

  val cells: List[Tile] =
    List.tabulate(this.numberOfCells)(_ => Tile(this.panel, DisplayValuesManager.CellSize.value))
  this.cells.foreach(tile => tile.backgroundImage_(ImageManager.CaveFloorTile.path))
  this.cells(26).placeCharacter(ImageManager.CharacterRight.path)

  val keyHandler: KeyHandler = KeyHandler()
  this.keyHandler.registerKeyAction(this.mainPanel, this.cells)

  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  this.setResizable(false)
  this.setVisible(true)
  this.pack()

object GUI extends App:
  val game: Game = new Game
