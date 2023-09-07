package model.cells.logic
import model.cells.{Cell, Direction, Item, PlantCell, RockCell, DoorCell}
import model.cells.logic.CellExtension.updateItem
import model.game.CurrentGame

object UseItemExtension:

  /** extension for adding new methods for interacting with power-ups
    */
  extension (cell: Cell)
    def usePowerUp(): Set[Cell] = cell match
      case _: RockCell if CurrentGame.itemHolder.itemOwned.contains(Item.Pick) =>
        cell.updateItem(Set(cell), newItem = Item.Pick)
      case _: PlantCell if CurrentGame.itemHolder.itemOwned.contains(Item.Axe) =>
        cell.updateItem(Set(cell), newItem = Item.Axe)
      case _: DoorCell if CurrentGame.itemHolder.itemOwned.contains(Item.Key) =>
        CurrentGame.itemHolder.itemOwned.collectFirst {
          case x if x != Item.Key => x
        }.toList ++ CurrentGame.itemHolder.itemOwned
          .dropWhile(_ != Item.Key)
          .drop(1)
        cell.updateItem(Set(cell), newItem = Item.Key)
      case _ =>
        Set.empty[Cell]
