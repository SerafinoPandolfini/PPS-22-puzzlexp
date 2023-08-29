package view

import controller.ProvaController
import model.cells.Direction
import utils.{ImageManager, DisplayValuesManager}

import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.{JComponent, JPanel, KeyStroke}
import javax.swing.SwingUtilities

case class KeyAction(keyCode: Int, imagePath: String, direction: Direction)

trait KeyHandler:
  def registerKeyAction(mainPanel: JPanel, cells: List[Tile]): Unit
  def keyAction(key: Int): Option[KeyAction]

object KeyHandler:
  private class KeyHandlerImpl() extends KeyHandler:

    val keyActions: List[KeyAction] = List(
      KeyAction(KeyEvent.VK_A, ImageManager.CHARACTER_LEFT.path, Direction.Left),
      KeyAction(KeyEvent.VK_D, ImageManager.CHARACTER_RIGHT.path, Direction.Right),
      KeyAction(KeyEvent.VK_W, ImageManager.CHARACTER_UP.path, Direction.Up),
      KeyAction(KeyEvent.VK_S, ImageManager.CHARACTER_DOWN.path, Direction.Down)
    )

    override def registerKeyAction(mainPanel: JPanel, cells: List[Tile]): Unit =
      keyActions.foreach(key =>
        mainPanel.registerKeyboardAction(
          (_: ActionEvent) => {
            cells.find(t => t.isCharacterHere).get.unplaceCharacter()
            mainPanel.repaint()
            val pos = ProvaController.movePlayer(key.direction)
            cells(pos._1 + (pos._2 * DisplayValuesManager.COLS.value)).placeCharacter(key.imagePath)
            mainPanel.revalidate()
          },
          KeyStroke.getKeyStroke(key.keyCode, 0),
          JComponent.WHEN_IN_FOCUSED_WINDOW
        )
      )

    override def keyAction(key: Int): Option[KeyAction] = this.keyActions.find(_.keyCode == key)

  def apply(): KeyHandler = KeyHandlerImpl()
