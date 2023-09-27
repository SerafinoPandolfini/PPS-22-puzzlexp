package view.game

import controller.game.GameController
import model.cells.Position
import model.cells.properties.{Direction, Item}
import model.gameMap.MinimapElement
import model.room.Room
import utils.extensions.paths.CellPathExtractor.extractCellPath
import utils.constants.PathManager.{ImagePath, PngExtension}
import view.game.pause.PauseGamePanel
import javax.swing.{ImageIcon, JPanel, SwingUtilities}
import java.awt.{BorderLayout, Image}
import java.net.URL

object ViewUpdater:

  extension (view: GameView)
    /** update the tiles with the current player position
      * @param position
      *   the position of the player
      * @param image
      *   the image repsenting the player
      */
    def updatePlayerImage(position: Position, image: URL): Unit =
      val updatedTile = view.tiles.get(position)
      println(position)
      updatedTile.get.playerImage = Option(ImageIcon(image).getImage)

    /** associate the [[MultiLayeredTile]]s with their respective images
      * @param room
      *   the [[Room]] to convert into images for the [[MultiLayeredTile]]s
      */
    def associateTiles(room: Room, zippedPaths: List[(String, String)]): Unit =
      view.tiles =
        view.tiles.keys.zip(zippedPaths).foldLeft(view.tiles) { case (tilesMap, ((x, y), (groundPath, itemPath))) =>
          val updatedTile = tilesMap((x, y))
          updatedTile.itemImage = Option(getCellImage(itemPath))
          updatedTile.cellImage = Option(getCellImage(groundPath))
          tilesMap.updated((x, y), updatedTile)
        }

    /** update the label for the specified item
      * @param item
      *   the item of the [[Label]] to update
      * @param amount
      *   the new amount of the specified item
      */
    def updateItemLabel(item: Item, amount: Int): Unit =
      view.itemLabels = view.itemLabels.map({
        case l if l.item == item => l.updateLabel(amount)
        case l                   => l
      })

    /** update the score of the player
      * @param score
      *   the current score of the player
      */
    def updateScore(score: Int): Unit =
      view.scoreLabel.setText(ToolbarElements.scoreText concat score.toString)

    /** called when the player reach the [[Item.GoalGem]]
      * @param playerScore
      *   the score accumulated by the player
      * @param totalScore
      *   the maximum score for the played [[model.gameMap.GameMap]]
      * @param percentage
      *   calculated as playerScore over totalScore
      */
    def endGame(playerScore: Int, totalScore: Int, percentage: Double): Unit =
      view.mainPanel.remove(view.toolbarPanel)
      view.mainPanel.remove(view.tilesPanel)
      EndGamePanel.createLabel(playerScore.toString, totalScore.toString, percentage.toString)
      view.mainPanel.add(view.endPanel)
      view.mainPanel.revalidate()

    /** get a specific image from its path
      * @param path
      *   the path for the image without file extension
      * @return
      *   the image
      */
    private def getCellImage(path: String): Image =
      val imageURL = Option(getClass.getClassLoader.getResource(ImagePath + path + PngExtension))
      if imageURL.isEmpty then ImageIcon("").getImage
      else ImageIcon(imageURL.get).getImage

    def pause(list: List[MinimapElement]): Unit =
      view.mainPanel.remove(view.toolbarPanel)
      view.mainPanel.remove(view.tilesPanel)
      view.mainPanel.add(
        PauseGamePanel(list, view.mainPanel.getWidth, view.mainPanel.getHeight).createPauseGamePanel(),
        BorderLayout.CENTER
      )
      view.mainPanel.repaint()
      view.mainPanel.revalidate()

    def back(): Unit =
      view.mainPanel.removeAll()
      view.mainPanel.add(view.toolbarPanel, BorderLayout.NORTH)
      view.mainPanel.add(view.tilesPanel, BorderLayout.CENTER)
      view.mainPanel.repaint()
      view.mainPanel.revalidate()
