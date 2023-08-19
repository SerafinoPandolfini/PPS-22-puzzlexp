package View

import java.awt.*
import javax.swing.*
import scala.collection.immutable.List
import Utils.{ColorManager, DisplayValuesManager, ImageManager}

import java.awt.event.{ActionEvent, ActionListener, KeyEvent}
import javax.swing.border.Border

class Game extends JFrame:
  val numberOfCells: Int = DisplayValuesManager.ROWS.value * DisplayValuesManager.COLS.value
  val panel = new JPanel(new GridLayout(DisplayValuesManager.ROWS.value, DisplayValuesManager.COLS.value))
  val mainPanel = new JPanel(new BorderLayout())
  val toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT))
  this.add(mainPanel)
  this.toolbarPanel.setBackground(ColorManager.TOOLBAR_BACKGROUND.color)
  this.toolbarPanel.setOpaque(true)
  this.toolbarPanel.setPreferredSize(
    new Dimension(
      DisplayValuesManager.COLS.value * DisplayValuesManager.CELL_SIZE.value,
      DisplayValuesManager.CELL_SIZE.value * DisplayValuesManager.TOOLBAR_HEIGHT.value
    )
  )

  val border: Border =
    BorderFactory.createLineBorder(
      ColorManager.TOOLBAR_BORDER.color,
      DisplayValuesManager.TOOLBAR_BORDER_THICKNESS.value
    )
  this.toolbarPanel.setBorder(this.border)
  val berryLabel: JLabel = new JLabel()
  this.berryLabel.setIcon(new ImageIcon(ImageManager.BERRY.path))
  this.toolbarPanel.add(this.berryLabel)

  this.mainPanel.add(this.toolbarPanel, BorderLayout.NORTH)
  this.mainPanel.add(this.panel, BorderLayout.CENTER)

  val cells: List[Tile] =
    List.tabulate(this.numberOfCells)(_ => Tile(this.panel, DisplayValuesManager.CELL_SIZE.value))
  this.cells.foreach(tile => tile.backgroundImage_(ImageManager.CAVE_FLOOR_TILE.path))
  this.cells.head.placeCharacter(ImageManager.CHARACTER_RIGHT.path)

  val keyHandler: KeyHandler = KeyHandler()
  this.keyHandler.registerKeyAction(this.mainPanel, this.cells)

  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  this.setResizable(false)
  this.setVisible(true)
  this.pack()

object GUI extends App:
  val game: Game = new Game
