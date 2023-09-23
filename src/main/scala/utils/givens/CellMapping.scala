package utils.givens

import model.cells.*
import utils.extensions.PositionExtension.*

/** extension for the mapping of the position of cells */
object CellMapping:
  extension (c: Cell)
    /** @param c
      *   cell whose string we need to compute
      * @param cells
      *   set of the adjacent cells of cell
      * @return
      *   the same set with the mapped position
      */
    def convertCellsPositions(cells: Set[Cell]): Set[Cell] =
      cells.map(cell =>
        cell match
          case cell: WallCell                => cell.copy(position = cell.position - c.position)
          case cell: BasicCell               => cell.copy(position = cell.position - c.position)
          case cell: ButtonBlockCell         => cell.copy(position = cell.position - c.position)
          case cell: CliffCell               => cell.copy(position = cell.position - c.position)
          case cell: PressurePlateBlockCell  => cell.copy(position = cell.position - c.position)
          case cell: TeleportDestinationCell => cell.copy(position = cell.position - c.position)
          case cell: HoleCell                => cell.copy(position = cell.position - c.position)
          case cell: CoveredHoleCell         => cell.copy(position = cell.position - c.position)
          case cell: RockCell                => cell.copy(position = cell.position - c.position)
          case cell: PlantCell               => cell.copy(position = cell.position - c.position)
          case cell: LockCell                => cell.copy(position = cell.position - c.position)
          case cell: TeleportCell            => cell.copy(position = cell.position - c.position)
          case cell: ButtonCell              => cell.copy(position = cell.position - c.position)
      )
