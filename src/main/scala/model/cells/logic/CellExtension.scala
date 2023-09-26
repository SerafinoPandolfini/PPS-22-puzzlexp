package model.cells.logic

import model.cells.*

import model.cells.logic.UpdateMethods.*
import model.cells.properties.{Direction, Item, PressableState}

object CellExtension:
  /** extension for adding new methods for interacting with cellItems and the player
    */
  extension (cell: Cell)
    /** Perform the necessary operations when the player walks in a cell
      * @param cells
      *   The set of all cells in the current room
      * @return
      *   the set of the modified cells in the room and the [[Position]] of the cell in which the player is now
      */
    def moveIn(cells: Set[Cell]): (Set[Cell], Position) =
      cell match
        case c: ButtonCell =>
          (pressed(cells, c.color) + c.copy(pressableState = PressableState.Pressed), cell.position)
        case _: TeleportCell =>
          (
            Set.empty,
            findTeleportDestination(cells) match
              case Some(value) => value.position
              case None        => cell.position
          )
        case _ => (Set.empty, cell.position)

    /** Perform the necessary operations wen the player walks out of a cell
      * @param cells
      *   The set of all cells in the current room
      * @return
      *   A set of modified cells after the player walks out
      */
    def moveOut(cells: Set[Cell]): Set[Cell] =
      cell match
        case c: CoveredHoleCell => Set(c.copy(cover = false))
        case _                     => Set.empty

    /** Updates the item in the cell and returns a set of modified cells based on the rules of the game.
      *
      * @param cells
      *   The set of all cells in the current room
      * @param newItem
      *   The new item to be placed in the cell
      * @param direction
      *   The direction in which the update is happening
      * @return
      *   A set of modified cells after the update
      */
    def updateItem(cells: Set[Cell], newItem: Item, direction: Direction = Direction.Up): Set[Cell] =
      cell match
        case c: BasicCell               => Set(c.copy(cellItem = newItem))
        case c: ButtonBlockCell         => Set(c.copy(cellItem = newItem))
        case c: CliffCell               => Set(c.copy(cellItem = newItem))
        case c: PressurePlateBlockCell  => Set(c.copy(cellItem = newItem))
        case c: TeleportDestinationCell => Set(c.copy(cellItem = newItem))
        case c: HoleCell                => updateHoleItem(c, newItem)
        case c: CoveredHoleCell         => updateCoveredHoleItem(c, newItem)
        case c: RockCell                => updateRockItem(c, newItem)
        case c: PlantCell               => updatePlantItem(c, newItem)
        case c: LockCell                => updateDoorItem(c, newItem)
        case _: TeleportCell               => updateTeleportItem(cells, newItem, direction)
        case c: ButtonCell =>
          newItem match
            case Item.Box =>
              pressed(cells, c.color) + c.copy(cellItem = newItem, pressableState = PressableState.Pressed)
            case _ => Set(c.copy(cellItem = newItem))
        case c: PressurePlateCell =>
          val pressableState = newItem match
            case Item.Box => PressableState.Pressed
            case _        => PressableState.NotPressed
          updatePressurePlate(cells, pressableState) + c.copy(
            cellItem = newItem,
            pressableState = pressableState
          )
        case _ => Set.empty[Cell]
