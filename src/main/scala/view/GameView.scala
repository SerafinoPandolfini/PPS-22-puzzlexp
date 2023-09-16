package view

import controller.GameController

import java.awt.*
import javax.swing.*
import scala.collection.immutable.{List, ListMap}
import utils.{ColorManager, ConstantUtils, ImageManager}

import java.awt.event.{ActionEvent, ActionListener, KeyEvent}
import javax.swing.border.Border
import model.room.*
import model.cells.Item
import model.cells.Cell.given
import model.cells.{BasicCell, Position, WallCell}
import model.gameMap.GameMap
import serialization.{JsonDecoder, JsonEncoder}
import view.GameView.{BasePath, PNGPath}
import utils.PathExtractor.extractPath

/** the standard game GUI
  */
class GameView(initialRoom: Room, initialPos: Position) extends JFrame:
  private var itemLabels = ToolbarElements.generateStandardToolbarElements()
  private val scoreLabel = ToolbarElements.createScoreLabel()
  val tilesPanel: JPanel = JPanel(GridLayout(ConstantUtils.Rows, ConstantUtils.Cols))
  val toolbarPanel: JPanel = createToolbarPanel()
  val mainPanel: JPanel = createMainPanel()
  val endPanel: JPanel = EndGamePanel.createEndGamePanel()
  val keyHandler: KeyHandler = KeyHandler()
  private var _tiles: ListMap[Position, MultiLayeredTile] =
    (for
      row <- 0 until ConstantUtils.Rows
      col <- 0 until ConstantUtils.Cols
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
    tile.setPreferredSize(Dimension(ConstantUtils.CellSize, ConstantUtils.CellSize))
    tilesPanel.add(tile)
    tile

  /** configure the base value for the view, give a first initialization for the [[MultiLayeredTile]] and show the
    * [[JFrame]]
    */
  private def configureFrame(): Unit =
    add(mainPanel)
    keyHandler.registerKeyAction(tilesPanel, _tiles)
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    associateTiles(initialRoom)
    updatePlayerImage(initialPos, ImageManager.CharacterDown.path)
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
    *
    * @param room
    *   the [[Room]] to convert into images for the [[MultiLayeredTile]]s
    */
  def associateTiles(room: Room): Unit =
    val groundPaths = room.cells.toList.sorted.map(c => extractPath(c, room.cells))
    val itemPaths = room.cells.toList.sorted.map(_.cellItem.toString)
    val zippedPaths = groundPaths zip itemPaths
    _tiles = _tiles.keys.zip(zippedPaths).foldLeft(_tiles) { case (tilesMap, ((x, y), (groundPath, itemPath))) =>
      val updatedTile = tilesMap((x, y))
      updatedTile.itemImage = Option(ImageIcon(s"$BasePath$itemPath$PNGPath").getImage)
      updatedTile.cellImage = Option(ImageIcon(s"$BasePath$groundPath$PNGPath").getImage)
      tilesMap.updated((x, y), updatedTile)
    }

  /** update the label for the specified item
    * @param item
    *   the item of the [[Label]] to update
    * @param amount
    *   the new amount of the specified item
    */
  def updateItemLabel(item: Item, amount: Int): Unit =
    itemLabels = itemLabels.map({
      case l if l.item == item => l.updateLabel(amount)
      case l                   => l
    })

  /** update the score of the player
    *
    * @param score
    *   the current score of the player
    */
  def updateScore(score: Int): Unit =
    scoreLabel.setText(ToolbarElements.scoreText concat score.toString)

  def endGame(playerScore: Int, totalScore: Int, percentage: Double): Unit =
    mainPanel.remove(toolbarPanel)
    mainPanel.remove(tilesPanel)
    EndGamePanel.createLabel(playerScore.toString, totalScore.toString, percentage.toString)
    mainPanel.add(endPanel)
    mainPanel.revalidate()

object GameView:
  val BasePath = "src/main/resources/img/"
  val PNGPath = ".png"
