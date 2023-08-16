package Model.Cells.Extension

import Model.Cells.WalkableType.Walkable
import Model.Cells.{BasicCell, ButtonBlockCell, ButtonCell, Cell, Direction, Item, PressableState}

object CellExtension:
  extension (cell: Cell)
    def updateItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      cell match
        case cell: BasicCell       => Set(cell.copy(cellItem = newItem))
        case cell: ButtonBlockCell => Set(cell.copy(cellItem = newItem))
        case cell: ButtonCell =>
          newItem match
            case Item.Box => pressed(newItem, cells)
            case _        => Set(cell.copy(cellItem = newItem))

    /** Set the state to "Pressed" */
    def pressed(item: Item = Item.Empty, cells: Set[Cell]): Set[Cell] =
      Set.from(
        cells
          .collect { case c: ButtonBlockCell => c }
          .filter(c => c.color == cell.asInstanceOf[ButtonCell].color)
          .map(c => c.copy(pressableState = PressableState.Pressed))
      ) + cell.asInstanceOf[ButtonCell].copy(cellItem = item, pressableState = PressableState.Pressed)
