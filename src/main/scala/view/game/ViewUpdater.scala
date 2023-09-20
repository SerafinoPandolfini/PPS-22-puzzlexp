package view.game

import model.cells.Position
import model.cells.properties.Item
import model.room.Room
import utils.PathExtractor.extractPath
import utils.constants.PathManager.{ImagePath, PngExtension}

import java.awt.Image
import java.net.URL
import javax.swing.ImageIcon

object ViewUpdater:

  extension (view: GameView)

    /** update the tiles with the current player position
     *
     * @param position
     * the position of the player
     * @param image
     * the image repsenting the player
     */
    def updatePlayerImage(position: Position, image: URL): Unit =
      val updatedTile = view.tiles.get(position)
      println(position)
      updatedTile.get.playerImage = Option(ImageIcon(image).getImage)

    /** associate the [[MultiLayeredTile]]s with their respective images
     *
     * @param room
     * the [[Room]] to convert into images for the [[MultiLayeredTile]]s
     */
    def associateTiles(room: Room): Unit =
      val groundPaths = room.cells.toList.sorted.map(c => extractPath(c, room.cells))
      val itemPaths = room.cells.toList.sorted.map(_.cellItem.toString)
      val zippedPaths = groundPaths zip itemPaths
      view.tiles = view.tiles.keys.zip(zippedPaths).foldLeft(view.tiles) { case (tilesMap, ((x, y), (groundPath, itemPath))) =>
        val updatedTile = tilesMap((x, y))
        updatedTile.itemImage = Option(getCellImage(itemPath))
        updatedTile.cellImage = Option(getCellImage(groundPath))
        tilesMap.updated((x, y), updatedTile)
      }

    /** update the label for the specified item
     *
     * @param item
     * the item of the [[Label]] to update
     * @param amount
     * the new amount of the specified item
     */
    def updateItemLabel(item: Item, amount: Int): Unit =
      view.itemLabels = view.itemLabels.map({
        case l if l.item == item => l.updateLabel(amount)
        case l => l
      })

    /** update the score of the player
     *
     * @param score
     * the current score of the player
     */
    def updateScore(score: Int): Unit =
      view.scoreLabel.setText(ToolbarElements.scoreText concat score.toString)

    def endGame(playerScore: Int, totalScore: Int, percentage: Double): Unit =
      view.mainPanel.remove(view.toolbarPanel)
      view.mainPanel.remove(view.tilesPanel)
      EndGamePanel.createLabel(playerScore.toString, totalScore.toString, percentage.toString)
      view.mainPanel.add(view.endPanel)
      view.mainPanel.revalidate()

    private def getCellImage(path: String): Image =
      val imageURL = Option(getClass.getClassLoader.getResource(ImagePath + path + PngExtension))
      if imageURL.isEmpty then ImageIcon("").getImage
      else ImageIcon(imageURL.get).getImage