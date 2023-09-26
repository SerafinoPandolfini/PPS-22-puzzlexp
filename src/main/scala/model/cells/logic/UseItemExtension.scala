package model.cells.logic

import model.cells.{Cell, PlantCell, RockCell, LockCell}
import model.cells.logic.CellExtension.updateItem
import model.cells.properties.{Direction, Item}
import model.game.CurrentGame

object UseItemExtension:

  /** extension for adding new methods for interacting with power-ups
    */
  extension (cell: Cell)
    /** use the powerUp updating correctly the set
      * @return
      *   a [[Set]] of [[Cell]]s containing the changed [[Cell]] if present
      */
    def usePowerUp(): Set[Cell] = cell match
      case _: RockCell if CurrentGame.itemHolder.itemOwned.contains(Item.Pick) =>
        cell.updateItem(Set(cell), newItem = Item.Pick)
      case _: PlantCell if CurrentGame.itemHolder.itemOwned.contains(Item.Axe) =>
        cell.updateItem(Set(cell), newItem = Item.Axe)
      case c: LockCell if CurrentGame.itemHolder.itemOwned.contains(Item.Key) && !c.open =>
        CurrentGame.removeItem(Item.Key)
        cell.updateItem(Set(cell), newItem = Item.Key)
      case _ =>
        Set.empty[Cell]
