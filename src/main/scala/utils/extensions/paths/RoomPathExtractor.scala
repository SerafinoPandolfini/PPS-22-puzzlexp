package utils.extensions.paths

import model.room.Room
import utils.extensions.paths.CellPathExtractor.extractCellPath

object RoomPathExtractor:

  extension(room: Room)
      /** convert the room into paths for its cells
       * @return the paths of all cells of the room
       */
    def extractRoomPath(): List[(String, String)] =
        room.cells.toList.sorted.map(_.extractCellPath(room.cells)) zip room.cells.toList.sorted.map(_.cellItem.toString)


