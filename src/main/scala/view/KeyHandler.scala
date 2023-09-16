package view

import controller.GameController
import model.cells.{Direction, Position}
import utils.ImageManager
import model.game.CurrentGame

import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.{AbstractAction, ImageIcon, JComponent, JPanel, KeyStroke, SwingUtilities}
import scala.collection.immutable.ListMap
import view.GameView.{BasePath, PNGPath}
import view.MultiLayeredTile

trait KeyHandler:
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
          tiles(CurrentGame.currentPosition).playerImage = Some(
            ImageIcon(ImageManager.CharacterDown.path).getImage
          )
        case _ => GameController.movePlayer(keyCode)

  def apply(): KeyHandler = KeyHandlerImpl()
