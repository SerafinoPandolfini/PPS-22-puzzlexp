package model.gameMap

import model.cells.Position
import model.room.Room
import utils.givens.ItemConversion.given_Conversion_Item_Int
import scala.util.Try
import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.cells.properties.Direction

class GameMap(val name: String, var rooms: Set[Room], val initialRoom: String, val initialPosition: Position):

  /** get the total points of the map
    */
  val totalPoints: Int =
    var points = 0
    for
      r <- rooms
      c <- r.cells
      v = c.cellItem
    yield points = points + v
    points

  /** get the room from it's name
    * @param roomName
    *   the name of the room to be returned
    * @return
    *   the room or an error if the roomName is not present
    */
  def getRoomFromName(roomName: String): Try[Room] =
    Try(rooms.find(_.name == roomName) match
      case Some(value) => value.createCopy()
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
    yield (toRoom.createCopy(), link.to)

  /** update a room that is in the map with a new version
    * @param room
    *   the new room
    * @return
    *   the modified set of rooms
    */
  def updateRoom(room: Room): Set[Room] = rooms.filterNot(_.name == room.name) + room

  /** create a copy of the current [[GameMap]]
    * @return
    *   the copy of the [[GameMap]]
    */
  def createCopy(): GameMap = GameMap(name, rooms, initialRoom, initialPosition)
