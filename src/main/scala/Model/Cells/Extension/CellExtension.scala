package Model.Cells.Extension

import Model.Cells.WalkableType.Walkable
import Model.Cells.{
  BasicCell,
  ButtonBlockCell,
  ButtonCell,
  Cell,
  CoveredHoleCell,
  HoleCell,
  CliffCell,
  Direction,
  Item,
  TeleportDestinationCell,
  TeleportCell,
  PressableState,
  SwitchBlockCell,
  SwitchBlockGroup,
  SwitchCell
}

import PositionExtension.+

object CellExtension:
  /** extension for adding new methods for interacting with cellItems and the player
    */
  extension (cell: Cell)

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
    def updateItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      cell match
        case cell: BasicCell               => Set(cell.copy(cellItem = newItem))
        case cell: ButtonBlockCell         => Set(cell.copy(cellItem = newItem))
        case cell: CliffCell               => Set(cell.copy(cellItem = newItem))
        case cell: SwitchBlockCell         => Set(cell.copy(cellItem = newItem))
        case cell: TeleportDestinationCell => Set(cell.copy(cellItem = newItem))
        case cell: HoleCell                => updateHoleItem(cell, newItem)
        case cell: CoveredHoleCell         => updateCoveredHoleItem(cell, newItem)
        case _: TeleportCell               => updateTeleportItem(cells, newItem, direction)
        case cell: ButtonCell =>
          newItem match
            case Item.Box => pressed(newItem, cells)
            case _        => Set(cell.copy(cellItem = newItem))
        case cell: SwitchCell =>
          newItem match
            case Item.Box =>
              Set.from(
                cells
                  .collect { case c: SwitchBlockCell => c }
                  .map(c => c.copy(pressableState = PressableState.Pressed))
              ) + cell.copy(cellItem = newItem, pressableState = PressableState.Pressed)
            case _ =>
              Set.from(
                cells
                  .collect { case c: SwitchBlockCell => c }
                  .map(c => c.copy(pressableState = PressableState.NotPressed))
              ) + cell.copy(cellItem = newItem, pressableState = PressableState.NotPressed)
        case _ => Set.empty[Cell]

    /** Set the state to "Pressed" */
    private def pressed(item: Item = Item.Empty, cells: Set[Cell]): Set[Cell] =
      Set.from(
        cells
          .collect { case c: ButtonBlockCell => c }
          .filter(c => c.color == cell.asInstanceOf[ButtonCell].color)
          .map(c => c.copy(pressableState = PressableState.Pressed))
      ) + cell.asInstanceOf[ButtonCell].copy(cellItem = item, pressableState = PressableState.Pressed)

    private def updateHoleItem(hCell: HoleCell, newItem: Item): Set[Cell] = newItem match
      case Item.Box =>
        if hCell.filled then Set(hCell.copy(cellItem = newItem))
        else Set(hCell.copy(cellItem = Item.Empty, filled = true))
      case _ => Set(hCell.copy(cellItem = Item.Empty))

    private def updateCoveredHoleItem(chCell: CoveredHoleCell, newItem: Item): Set[Cell] = newItem match
      case Item.Box =>
        if chCell.filled then Set(chCell.copy(cellItem = newItem))
        else Set(chCell.copy(cellItem = Item.Empty, cover = false, filled = true))
      case _ => Set.empty[Cell]

    private def updateTeleportItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      cells.collectFirst { case c: TeleportDestinationCell =>
        c
      } match
        case Some(tdCell) =>
          cells.collectFirst {
            case dCell if dCell.position == tdCell.position + direction.coordinates => dCell
          } match
            case Some(destCell) => destCell.updateItem(cells, Item.Box, direction)
            case None           => Set.empty[Cell]
        case None => Set.empty[Cell]
