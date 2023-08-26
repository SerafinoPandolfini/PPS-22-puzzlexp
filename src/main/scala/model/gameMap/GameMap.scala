package model.gameMap

import model.cells.{Direction, Position}
import model.room.Room

import scala.util.Try
import exceptions.{LinkNotFoundException, RoomNotFoundException}

class GameMap(val name: String, val rooms: Set[Room], val initialRoom: String, val initialPosition: Position):

  /** get the room from it's name
    * @param roomName
    *   the name of the room to be returned
    * @return
    *   the room or an error if the roomName is not present
    */
  def getRoomFromName(roomName: String): Try[Room] =
    Try(rooms.find(_.name == roomName) match
      case Some(value) => value
      case _           => throw new RoomNotFoundException
    )

  /** try to change the room
    * @param position
    *   the position in the previous room
    * @param roomName
    *   the previous room name
    * @return
    *   retrieve the tuple of next room and initial position if found, else an error
    */
  def changeRoom(position: Position, roomName: String, direction: Direction): Try[(Room, Position)] =
    for
      room <- getRoomFromName(roomName)
      link <- Try(
        room.links.find(_.from == position).filter(_.direction == direction).getOrElse(throw new LinkNotFoundException)
      )
      toRoom <- getRoomFromName(link.toRoom)
    yield (toRoom, link.to)
