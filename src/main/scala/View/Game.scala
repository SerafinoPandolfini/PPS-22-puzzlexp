package View

import java.awt.*
import javax.swing.*
import javax.swing.border.LineBorder
import scala.collection.immutable.List
import Utils.ImageManager

class Game extends JFrame:
  val cellSize: Int = 32
  private val rows = Toolkit.getDefaultToolkit.getScreenSize.height * 13 / 16 / this.cellSize
  private val cols = Toolkit.getDefaultToolkit.getScreenSize.height * 13 / 16 / cellSize
  val numberOfCells: Int = rows * cols
  val panel = new JPanel(new GridLayout(rows, cols))
  this.add(panel)
  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  private val cells: List[Tile] = List.tabulate(this.numberOfCells)(i => Tile(this.panel, this.cellSize))

  cells.foreach { tile =>
    tile.backgroundImage_(ImageManager.CAVE_FLOOR_TILE.path)
  }

  cells.head.placeCharacter("")

  this.setLocationRelativeTo(null)
  this.setVisible(true)
  this.pack()

object GUI extends App:
  val game: Game = new Game
