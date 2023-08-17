package Model.GameMap

import Model.Cells.Position
import Model.Room.Room
import scala.util.Try
import Exceptions.RoomNotFoundException

class GameMap(val name: String, val rooms: Set[Room], val initialRoom: String, val initialPosition: Position):
  /** get the room from it's name
    * @param roomName
    *   the name of the room to be returned
    * @return
    *   the room
    */
  def getRoomFromName(roomName: String): Try[Room] =
    Try(rooms.find(_.name == roomName) match
      case Some(value) => value
      case _           => throw new RoomNotFoundException
    )
