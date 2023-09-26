package view.game

import controller.game.GameController
import model.cells.Cell.given
import model.cells.properties.Item
import model.cells.{BasicCell, Position, WallCell}
import model.gameMap.GameMap
import model.room.*
import serialization.{JsonDecoder, JsonEncoder}
import utils.constants.{ColorManager, GraphicManager, ImageManager}
import view.game.EndGamePanel
import view.game.ViewUpdater.*

import java.awt.*
import java.awt.event.{ActionEvent, ActionListener, KeyEvent}
import javax.swing.*
import javax.swing.border.Border
import scala.collection.immutable.{List, ListMap}

/** the standard game GUI
  */
class GameView(initialRoom: Room, initialPos: Position) extends JFrame:
  private[game] var itemLabels = ToolbarElements.generateStandardToolbarElements()
  private[game] val scoreLabel = ToolbarElements.createScoreLabel()
  val tilesPanel: JPanel = JPanel(GridLayout(GraphicManager.Rows, GraphicManager.Cols))
  val toolbarPanel: JPanel = createToolbarPanel()
  val mainPanel: JPanel = createMainPanel()
  val endPanel: JPanel = EndGamePanel.createEndGamePanel()
  val keyHandler: KeyHandler = KeyHandler()
  private var _tiles: ListMap[Position, MultiLayeredTile] =
    (for
      row <- 0 until GraphicManager.Rows
      col <- 0 until GraphicManager.Cols
      position = (col, row)
    yield position -> createTile()).to(ListMap)
  configureFrame()

  /** @return
    *   the list of tiles shown
    */
  def tiles: ListMap[Position, MultiLayeredTile] = _tiles

  def tiles_=(newTiles: ListMap[Position, MultiLayeredTile]): Unit = _tiles = newTiles

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
    val toolbarPanel = ToolbarPanel.createBaseToolbarPanel()
    toolbarPanel.add(ToolbarElements.createPauseButton())
    for l <- itemLabels
    yield toolbarPanel.add(l.label)
    toolbarPanel.add(scoreLabel)

    toolbarPanel

  /** @return
    *   a simple [[MultiLayeredTile]] with only default images
    */
  private def createTile(): MultiLayeredTile =
    val tile = MultiLayeredTile()
    tile.setPreferredSize(Dimension(GraphicManager.CellSize, GraphicManager.CellSize))
    tilesPanel.add(tile)
    tile

  /** configure the base value for the view, give a first initialization for the [[MultiLayeredTile]] and show the
    * [[JFrame]]
    */
  private def configureFrame(): Unit =
    add(mainPanel)
    keyHandler.registerKeyAction(tilesPanel, _tiles)
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    this.associateTiles(initialRoom)
    this.updatePlayerImage(initialPos, ImageManager.CharacterDown.path)
    setResizable(false)
    setVisible(true)
    pack()
