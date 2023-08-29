package view

import java.awt.*
import javax.swing.*
import scala.collection.immutable.List
import utils.{ColorManager, DisplayValuesManager, ImageManager}

import java.awt.event.{ActionEvent, ActionListener, KeyEvent}
import javax.swing.border.Border
import model.room.*
import model.cells.Cell.given
import model.cells.{BasicCell, Position, WallCell}
import view.GameView.{BasePath, PNGPath}
import utils.PathExtractor.extractPath

/** the game GUI
  */
class GameView(initialRoom: Room, initialPos: Position) extends JFrame:
  // TODO: secondo me Game prende initial position e ha il metodo chargeRoom per caricare le stanze
  val numberOfCells = DisplayValuesManager.Rows.value * DisplayValuesManager.Cols.value
  // game interface
  val tilesPanel = JPanel(GridLayout(DisplayValuesManager.Rows.value, DisplayValuesManager.Cols.value))
  val toolbarPanel = createToolbarPanel()
  val mainPanel = createMainPanel()
  add(mainPanel)
  // tiles list
  private var tiles: List[Tile] =
    List.tabulate(numberOfCells)(_ => Tile(tilesPanel, DisplayValuesManager.CellSize.value))
  associateTiles(initialRoom)

  // key handling
  val keyHandler: KeyHandler = KeyHandler()
  keyHandler.registerKeyAction(mainPanel, tiles)

  // GUI properties
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setResizable(false)
  setVisible(true)
  pack()

  /** create the game main panel containing the tiles panel and the toolbar panel
    * @return
    *   the panel
    */
  private def createMainPanel(): JPanel =
    val p = JPanel(BorderLayout())
    p.add(toolbarPanel, BorderLayout.NORTH)
    p.add(tilesPanel, BorderLayout.CENTER)
    p

  /** create the toolbar panel for the items collected and the player score
    * @return
    *   the panel
    */
  private def createToolbarPanel(): JPanel =
    val toolbarPanel = JPanel(FlowLayout(FlowLayout.LEFT))
    toolbarPanel.setBackground(ColorManager.ToolbarBackground.color)
    toolbarPanel.setOpaque(true)
    toolbarPanel.setPreferredSize(
      Dimension(
        DisplayValuesManager.Cols.value * DisplayValuesManager.CellSize.value,
        DisplayValuesManager.CellSize.value * DisplayValuesManager.ToolbarHeight.value
      )
    )
    val border = BorderFactory.createLineBorder(
      ColorManager.ToolbarBorder.color,
      DisplayValuesManager.ToolbarBorderThickness.value
    )
    toolbarPanel.setBorder(border)
    val berryLabel = JLabel()
    berryLabel.setIcon(ImageIcon(ImageManager.Berry.path))
    toolbarPanel.add(berryLabel)
    toolbarPanel

  /** initialize the [[Tile]]s for
    * @return
    */
  def associateTiles(room: Room): Unit =
    println(tiles.size)
    val cellsPaths = room.cells.toList.sorted.map(extractPath(_))
    tiles.zip(cellsPaths).foreach { case (tile, imagePath) =>
      tile.groundImage(BasePath.concat(imagePath).concat(PNGPath))
    }
    tiles.head.placeCharacter(ImageManager.CharacterRight.path)
    tiles

  def test(): Unit =
    val tile = tiles.head
    tile.groundImage(BasePath + "test" + PNGPath)
    val tile2 = tiles.tail.head
    tile2.groundImage(BasePath + "test" + PNGPath)
object GameView:
  val BasePath = "src/main/resources/img/"
  val PNGPath = ".png"

object GUI extends App:
  val room = RoomBuilder().borderWalls().standardize.build
  val room2 = RoomBuilder().borderWalls().addCell(BasicCell((0, 0))).standardize.addCell(WallCell(-1,-1)).build
  val game: GameView = GameView(room, (0, 0))
  println(room.cells.size)
  println(room2.cells.size)
  //game.associateTiles(room2)
  //game.test()
