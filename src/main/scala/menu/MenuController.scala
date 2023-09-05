package menu

import menu.MenuView

object MenuController:
  private var _view: MenuView = _

  def view: MenuView = _view

  def start(): Unit =
    _view = MenuView()

object Start extends App:
  MenuController.start()
