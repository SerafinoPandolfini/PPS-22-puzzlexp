package menu

import menu.MenuController.continue
import menu.MenuView

object MenuController:
  private var _view: MenuView = _
  private var _continue: Boolean = false

  /** return the MenuView */
  def view: MenuView = _view

  /** return true if the player started the game */
  def continue: Boolean = _continue
  def continue_=(value: Boolean): Unit = _continue = value

  def start(): Unit =
    _view = MenuView()

  def startTheGame(): Boolean =
    if (continue) println("continue the game")
    else MenuController.start()
    continue

object Start extends App:
  MenuController.startTheGame()
