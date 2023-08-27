package model.cells.extension

import model.cells.{Cell, Direction, Item, PlantCell, RockCell}
import model.cells.logic.CellExtension.updateItem
import model.room.ItemHolder

object PowerUpExtension:

  /** extension for adding new methods for interacting with power-ups
    */
  extension (cell: Cell)
    def usePowerUp(powerUp: Item): Set[Cell] = (cell, powerUp) match
      case (_: RockCell, Item.Pick) => cell.updateItem(Set(cell), newItem = Item.Pick, Direction.Up)
      case (_: PlantCell, Item.Axe) => cell.updateItem(Set(cell), newItem = Item.Axe, Direction.Up)
      case _                        => Set(cell)
