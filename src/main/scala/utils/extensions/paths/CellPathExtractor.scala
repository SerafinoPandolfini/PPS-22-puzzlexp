package utils.extensions.paths

import model.cells.Cell
import utils.extensions.RoomCellsRepresentation.cellToString
import utils.extensions.paths.PathValue.{PathSplit, NoPath, PathStart, PathDeadly}
import utils.extensions.paths.WallPathExtractor.extractWallPath
import utils.extensions.paths.SpecificPathExtractor.{
  extractColorPath,
  extractCoveredPath,
  extractPressedPath,
  extractWalkablePath
}

object CellPathExtractor:

  extension (cell: Cell)
    
    /** convert the provided [[Cell]] to its image path
      * @param cells
      *   the other cells in the same room
      */
    def extractCellPath(cells: Set[Cell] = Set.empty[Cell]): String =
      val namePath = PathSplit concat cellToString(cell, true)
      val walkablePath = extractWalkablePath(cell)
      val coveredPath = extractCoveredPath(cell)
      val colorPath = extractColorPath(cell)
      val pressedPath = extractPressedPath(cell)
      val deadlyPath = if (cell.isDeadly) PathDeadly else NoPath
      val wallPath = extractWallPath(cell, cells)
      s"$PathStart$namePath$walkablePath$deadlyPath$coveredPath$colorPath$pressedPath$wallPath"
