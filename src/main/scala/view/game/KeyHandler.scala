package view.game

import controller.game.GameController
import model.cells.Position
import model.cells.properties.Direction
import model.game.CurrentGame
import utils.constants.ImageManager
import view.game.KeyHandler
import view.game.ViewUpdater.pause
import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.*
import scala.collection.immutable.ListMap

trait KeyHandler:
  /** @param mainPanel
    *   the panel
    * @param tiles
    *   the listMap with the position of the cells as key and the tile as value. each input key is stored with the
    *   related action
    */
  def registerKeyAction(mainPanel: JPanel, cells: ListMap[Position, MultiLayeredTile]): Unit

object KeyHandler:
  private class KeyHandlerImpl() extends KeyHandler:
    private val keyImageMap: List[Int] = List(
      KeyEvent.VK_A,
      KeyEvent.VK_D,
      KeyEvent.VK_W,
      KeyEvent.VK_S,
      KeyEvent.VK_R
    )

    /** @param mainPanel
      *   the panel
      * @param tiles
      *   the listMap with the position of the cells as key and the tile as value. each input key is stored with the
      *   related action
      */
    override def registerKeyAction(mainPanel: JPanel, tiles: ListMap[Position, MultiLayeredTile]): Unit =
      val actionMap = mainPanel.getActionMap
      val inputMap = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)

      keyImageMap.foreach { keyCode =>
        val action = createAction(keyCode, tiles, mainPanel)
        val keyStroke = KeyStroke.getKeyStroke(keyCode, 0)
        val actionKey = s"keyAction_$keyCode"
        actionMap.put(actionKey, action)
        inputMap.put(keyStroke, actionKey)
      }

    /** Creates the action. Removes the image of the player in the previous tile and draws it in the next one */
    private def createAction(
        keyCode: Int,
        tiles: ListMap[Position, MultiLayeredTile],
        mainPanel: JPanel
    ): AbstractAction = (_: ActionEvent) =>
      val characterTile = tiles.find(t => t._2.playerImage.isDefined).get
      characterTile._2.playerImage = Option.empty
      mainPanel
        .repaint()
      keyCode match
        case KeyEvent.VK_R =>
          GameController.resetRoom()
          tiles(CurrentGame.currentPosition).playerImage = Option(ImageIcon(ImageManager.CharacterDown.path).getImage)
        case _ => GameController.movePlayer(keyCode)

  def apply(): KeyHandler = KeyHandlerImpl()
