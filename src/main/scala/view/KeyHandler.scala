package view

import controller.ProvaController
import model.cells.Direction
import utils.{DisplayValuesManager, ImageManager}

import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.{JComponent, JPanel, KeyStroke}
import javax.swing.SwingUtilities
import javax.swing.AbstractAction

trait KeyHandler:
  def registerKeyAction(mainPanel: JPanel, cells: List[Tile]): Unit

object KeyHandler:
  private class KeyHandlerImpl() extends KeyHandler:

    private val keyImageMap: Map[Int, String] = Map(
      KeyEvent.VK_A -> ImageManager.CharacterLeft.path,
      KeyEvent.VK_D -> ImageManager.CharacterRight.path,
      KeyEvent.VK_W -> ImageManager.CharacterUp.path,
      KeyEvent.VK_S -> ImageManager.CharacterDown.path
    )

    override def registerKeyAction(mainPanel: JPanel, tiles: List[Tile]): Unit =
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
        tiles: List[Tile],
        mainPanel: JPanel
    ): AbstractAction = new AbstractAction {
      override def actionPerformed(e: ActionEvent): Unit =
        val characterTile = tiles.find(t => t.isCharacterHere).get
        characterTile.unplaceCharacter()
        mainPanel.repaint()

        val pos = ProvaController.movePlayer(keyCode)
        val newCharacterTileIndex = pos._1 + (pos._2 * DisplayValuesManager.Cols.value)
        tiles(newCharacterTileIndex).placeCharacter(imagePath)

        mainPanel.revalidate()

    }

  def apply(): KeyHandler = KeyHandlerImpl()
