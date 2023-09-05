package view

import controller.GameController

import java.awt.*
import javax.swing.*
import scala.collection.immutable.{List, ListMap}
import utils.{ColorManager, DisplayValuesManager, ImageManager}

import java.awt.event.{ActionEvent, ActionListener, KeyEvent}
import javax.swing.border.Border
import model.room.*
import model.cells.Cell.given
import model.cells.{BasicCell, Position, WallCell}
import model.gameMap.GameMap
import serialization.{JsonDecoder, JsonEncoder}
import view.GameView.{BasePath, PNGPath}
import utils.PathExtractor.extractPath

/** the standard game GUI
  */
class GameView(initialRoom: Room, initialPos: Position) extends JFrame:
  // game interface
  val tilesPanel: JPanel = JPanel(GridLayout(DisplayValuesManager.Rows.value, DisplayValuesManager.Cols.value))
  val toolbarPanel: JPanel = createToolbarPanel()
  val mainPanel: JPanel = createMainPanel()
  // key handling
  val keyHandler: KeyHandler = KeyHandler()
  // tiles list
  private var _tiles: ListMap[Position, MultiLayeredTile] =
    (for
      row <- 0 until DisplayValuesManager.Rows.value
      col <- 0 until DisplayValuesManager.Cols.value
      position = (col, row)
    yield position -> createTile()).to(ListMap)
  configureFrame()

  /** @return
    *   the list of tiles shown
    */
  def tiles: ListMap[Position, MultiLayeredTile] = _tiles

  /** create the game main panel containing the tiles panel and the toolbar panel
    *
    * @return
    *   the panel
    */
  private def createMainPanel(): JPanel =
    val main = JPanel(BorderLayout())
    main.add(toolbarPanel, BorderLayout.NORTH)
    main.add(tilesPanel, BorderLayout.CENTER)
    main

  /** create the toolbar panel for the items collected and the player score
    *
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

  /** @return
    *   a simple [[MultiLayeredTile]] with no images
    */
  private def createTile(): MultiLayeredTile =
    val tile = MultiLayeredTile()
    tile.setPreferredSize(Dimension(DisplayValuesManager.CellSize.value, DisplayValuesManager.CellSize.value))
    tilesPanel.add(tile)
    tile

  /** configure the base value for the view, give a first initialization for the [[MultiLayeredTile]] and show the
    * [[JFrame]]
    */
  private def configureFrame(): Unit =
    add(mainPanel)
    keyHandler.registerKeyAction(mainPanel, _tiles)
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    associateTiles(initialRoom)
    updatePlayerImage(initialPos, ImageManager.CharacterRight.path)
    setResizable(false)
    setVisible(true)
    pack()

  /** update the tiles with the current player position
    * @param position
    *   the position of the player
    * @param image
    *   the image repsenting the player
    */
  def updatePlayerImage(position: Position, image: String): Unit =
    val updatedTile = _tiles.get(position)
    println(position)
    updatedTile.get.playerImage = Option(ImageIcon(image).getImage)

  /** associate the [[MultiLayeredTile]]s with their respective images
    */
  def associateTiles(room: Room): Unit =
    val groundPaths = room.cells.toList.sorted.map(extractPath)
    val itemPaths = room.cells.toList.sorted.map(_.cellItem.toString)
    val zippedPaths = groundPaths zip itemPaths
    _tiles = _tiles.keys.zip(zippedPaths).foldLeft(_tiles) { case (tilesMap, ((x, y), (groundPath, itemPath))) =>
      val updatedTile = tilesMap((x, y))
      updatedTile.itemImage = Option(ImageIcon(s"$BasePath$itemPath$PNGPath").getImage)
      updatedTile.cellImage = Option(ImageIcon(s"$BasePath$groundPath$PNGPath").getImage)
      tilesMap.updated((x, y), updatedTile)
    }

object GameView:
  val BasePath = "src/main/resources/img/"
  val PNGPath = ".png"
