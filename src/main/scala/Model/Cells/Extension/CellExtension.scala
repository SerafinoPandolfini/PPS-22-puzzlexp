package Model.Cells.Extension

import Model.Cells.{BasicCell, Cell, Direction, Item}

object CellExtension:
  extension (cell: Cell)
    def updateItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      val newCells: Set[Cell] = cell match
        case cell: BasicCell => Set(cell.copy(cellItem = newItem))

      cells.map(cell =>
        newCells.find(_.position == cell.position) match
          case Some(c) => c
          case None    => cell
      )
