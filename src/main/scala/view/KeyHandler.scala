package view

import utils.ImageManager

import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.{JComponent, JPanel, KeyStroke}

case class KeyAction(keyCode: Int, imagePath: String)

trait KeyHandler:
  def registerKeyAction(mainPanel: JPanel, cells: List[Tile]): Unit
  def keyAction(key: Int): Option[KeyAction]

object KeyHandler:
  private class KeyHandlerImpl() extends KeyHandler:

    val keyActions: List[KeyAction] = List(
      KeyAction(KeyEvent.VK_A, ImageManager.CHARACTER_LEFT.path),
      KeyAction(KeyEvent.VK_D, ImageManager.CHARACTER_RIGHT.path),
      KeyAction(KeyEvent.VK_W, ImageManager.CHARACTER_UP.path),
      KeyAction(KeyEvent.VK_S, ImageManager.CHARACTER_DOWN.path)
    )

    override def registerKeyAction(mainPanel: JPanel, cells: List[Tile]): Unit =
      keyActions.foreach(key =>
        mainPanel.registerKeyboardAction(
          (_: ActionEvent) => {
            cells.head.placeCharacter(key.imagePath)
          },
          KeyStroke.getKeyStroke(key.keyCode, 0),
          JComponent.WHEN_IN_FOCUSED_WINDOW
        )
      )

    override def keyAction(key: Int): Option[KeyAction] = this.keyActions.find(_.keyCode == key)

  def apply(): KeyHandler = KeyHandlerImpl()
