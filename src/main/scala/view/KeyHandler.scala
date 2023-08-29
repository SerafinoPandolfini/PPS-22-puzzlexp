package view

import controller.ProvaController
import model.cells.Direction
import utils.{DisplayValuesManager, ImageManager}

import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.{JComponent, JPanel, KeyStroke}
import javax.swing.SwingUtilities
import javax.swing.AbstractAction

case class KeyAction(imagePath: String)

trait KeyHandler:
  def registerKeyAction(mainPanel: JPanel, cells: List[Tile]): Unit
  def keyAction(key: Int): Option[KeyAction]

object KeyHandler:
  private class KeyHandlerImpl() extends KeyHandler:

    private val keyImageMap: Map[Int, String] = Map(
      KeyEvent.VK_A -> ImageManager.CharacterLeft.path,
      KeyEvent.VK_D -> ImageManager.CharacterRight.path,
      KeyEvent.VK_W -> ImageManager.CharacterUp.path,
      KeyEvent.VK_S -> ImageManager.CharacterDown.path
    )

    /*override def registerKeyAction(mainPanel: JPanel, cells: List[Tile]): Unit =
      keyActions.foreach(key =>
        mainPanel.registerKeyboardAction(
          (_: ActionEvent) => {
            cells.find(t => t.isCharacterHere).get.unplaceCharacter()
            mainPanel.repaint()
            val pos = ProvaController.movePlayer(key.keyCode)
            cells(pos._1 + (pos._2 * DisplayValuesManager.Cols.value)).placeCharacter(key.imagePath)
            mainPanel.revalidate()
          },
          KeyStroke.getKeyStroke(key.keyCode, 0),
          JComponent.WHEN_IN_FOCUSED_WINDOW
        )
      )*/
    override def registerKeyAction(mainPanel: JPanel, tiles: List[Tile]): Unit =
      val actionMap = mainPanel.getActionMap
      val inputMap = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)

      keyImageMap.foreach { (keyCode, imagePath) =>
        val action = new AbstractAction:
          def actionPerformed(e: ActionEvent): Unit =
            tiles.find(t => t.isCharacterHere).get.unplaceCharacter()
            mainPanel.repaint()
            println("entro")
            val pos = ProvaController.movePlayer(keyCode)
            tiles(pos._1 + (pos._2 * DisplayValuesManager.Cols.value)).placeCharacter(imagePath)
            mainPanel.revalidate()

        val keyStroke = KeyStroke.getKeyStroke(keyCode, 0)
        val actionKey = s"keyAction_$keyCode"
        actionMap.put(actionKey, action)
        inputMap.put(keyStroke, actionKey)
      }

    override def keyAction(key: Int): Option[KeyAction] = keyImageMap.get(key).map(view.KeyAction.apply)

  def apply(): KeyHandler = KeyHandlerImpl()
