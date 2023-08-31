package view

import controller.GameController
import model.cells.{Direction, Position}
import utils.{DisplayValuesManager, ImageManager}

import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.{AbstractAction, ImageIcon, JComponent, JPanel, KeyStroke, SwingUtilities}
import scala.collection.immutable.ListMap
import view.GameView.{BasePath, PNGPath}
import view.MultiLayeredTile

trait KeyHandler:
  def registerKeyAction(mainPanel: JPanel, cells: ListMap[Position, MultiLayeredTile]): Unit

object KeyHandler:
  private class KeyHandlerImpl() extends KeyHandler:

    private val keyImageMap: Map[Int, String] = Map(
      KeyEvent.VK_A -> ImageManager.CharacterLeft.path,
      KeyEvent.VK_D -> ImageManager.CharacterRight.path,
      KeyEvent.VK_W -> ImageManager.CharacterUp.path,
      KeyEvent.VK_S -> ImageManager.CharacterDown.path
    )

    override def registerKeyAction(mainPanel: JPanel, tiles: ListMap[Position, MultiLayeredTile]): Unit =
      val actionMap = mainPanel.getActionMap
      val inputMap = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)

      keyImageMap.foreach { (keyCode, imagePath) =>
        val action = createAction(keyCode, imagePath, tiles, mainPanel)
        val keyStroke = KeyStroke.getKeyStroke(keyCode, 0)
        val actionKey = s"keyAction_$keyCode"
        actionMap.put(actionKey, action)
        inputMap.put(keyStroke, actionKey)
      }

    private def createAction(
        keyCode: Int,
        imagePath: String,
        tiles: ListMap[Position, MultiLayeredTile],
        mainPanel: JPanel
    ): AbstractAction = new AbstractAction {
      override def actionPerformed(e: ActionEvent): Unit =
        val characterTile = tiles.find(t => t._2.playerImage.isDefined).get
        characterTile._2.playerImage = Option.empty
        mainPanel.repaint()
        val pos = GameController.movePlayer(keyCode)
        println(pos)
        // val newCharacterTileIndex = pos._1 + (pos._2 * DisplayValuesManager.Cols.value)
        tiles(pos).playerImage = Some(ImageIcon(imagePath).getImage)
        mainPanel.revalidate()

    }

  def apply(): KeyHandler = KeyHandlerImpl()
