package Model.Cells.Extension

import Model.Cells.{Cell, Direction, Item, RockCell}
import Model.Cells.Extension.CellExtension.updateItem
import Model.Room.ItemHolder

object PowerUpExtension:

  /** extension for adding new methods for interacting with power-ups
    */
  extension (cell: Cell)
    def usePowerUp(powerUp: Item): Set[Cell] = (cell, powerUp) match
      case (_: RockCell, Item.Pick) => cell.updateItem(Set(cell), newItem = Item.Pick, Direction.Up)
      // case (_: piantaCell, PowerUp.ascia ) => true
      case _ => Set(cell)
