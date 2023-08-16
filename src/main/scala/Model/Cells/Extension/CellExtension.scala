package Model.Cells.Extension

import Model.Cells.{BasicCell, Cell, CoveredHoleCell, Direction, HoleCell, Item}

object CellExtension:
  extension (cell: Cell)
    def updateItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      cell match
        case cell: BasicCell => Set(cell.copy(cellItem = newItem))
        case cell: HoleCell =>
          newItem match
            case Item.Box =>
              if cell.filled then Set(cell.copy(cellItem = newItem))
              else Set(cell.copy(cellItem = Item.Empty, filled = true))
            case _ => Set(cell.copy(cellItem = Item.Empty))
        case cell: CoveredHoleCell =>
          newItem match
            case Item.Box =>
              if cell.filled then Set(cell.copy(cellItem = newItem))
              else Set(cell.copy(cellItem = Item.Empty, cover = false, filled = true))
            case _ => Set.empty[Cell]
        case _ => Set.empty[Cell]