package utils.extensions.paths

import model.cells.properties.{Colorable, PressableState, WalkableType}
import model.cells.{Cell, CliffCell, ButtonCell}
import model.cells.traits.CoveredHole
import utils.extensions.paths.PathValue.{PathWalkable, NoPath, PathSplit, PathCovered, PathPressed}

object SpecificPathExtractor:

  /** add the walkability to a cellRepresentation
   * @param cell the cell to check
   * @return the segment of path for walkability if the cell is walkable, nothing otherwise
   */
  private[paths] def extractWalkablePath(cell: Cell): String = cell.walkableState match
    case WalkableType.Walkable(b) => if (b) PathWalkable else NoPath
    case WalkableType.DirectionWalkable(_) =>
      cell match
        case cliffCell: CliffCell => s"$PathSplit${cliffCell.direction.toString.toUpperCase}"
        case _                    => NoPath

  /** add the covered to a cellRepresentation
   * @param cell the cell to check
   * @return the segment of path for covered if the cell have a cover, nothing otherwise
   */
  private[paths] def extractCoveredPath(cell: Cell): String = cell match
    case coveredCell: Cell with CoveredHole => if (coveredCell.cover) PathCovered else NoPath
    case _                                  => NoPath

  /** add the color to a cellRepresentation
   * @param cell the cell to check
   * @return the segment of path for the specific color if the cell have the trait colorable, nothing otherwise
   */
  private[paths] def extractColorPath(cell: Cell): String = cell match
    case colorCell: Cell with Colorable => s"$PathSplit${colorCell.color.toString.toUpperCase}"
    case _                              => NoPath

  /** add the pressed to the cellRepresentation
   * @param cell the cell to check
   * @return the segment of path for the pressed state of the cell, nothing otherwise
   */
  private[paths] def extractPressedPath(cell: Cell): String = cell match
    case buttonCell: ButtonCell => if (buttonCell.pressableState == PressableState.Pressed) PathPressed else NoPath
    case _                      => NoPath
