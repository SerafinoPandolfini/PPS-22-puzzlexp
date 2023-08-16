package Model.Cells.Extension

import Model.Cells.WalkableType.Walkable
import Model.Cells.{
  BasicCell,
  ButtonBlockCell,
  ButtonCell,
  Cell,
  CliffCell,
  Direction,
  Item,
  PressableState,
  SwitchBlockCell,
  SwitchBlockGroup,
  SwitchCell
}

object CellExtension:
  extension (cell: Cell)
    def updateItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      cell match
        case cell: BasicCell       => Set(cell.copy(cellItem = newItem))
        case cell: ButtonBlockCell => Set(cell.copy(cellItem = newItem))
        case cell: CliffCell       => Set(cell.copy(cellItem = newItem))
        case cell: SwitchBlockCell => Set(cell.copy(cellItem = newItem))
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

    /** Set the state to "Pressed" */
    def pressed(item: Item = Item.Empty, cells: Set[Cell]): Set[Cell] =
      Set.from(
        cells
          .collect { case c: ButtonBlockCell => c }
          .filter(c => c.color == cell.asInstanceOf[ButtonCell].color)
          .map(c => c.copy(pressableState = PressableState.Pressed))
      ) + cell.asInstanceOf[ButtonCell].copy(cellItem = item, pressableState = PressableState.Pressed)
