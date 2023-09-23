package model.gameMap

import model.cells.Position
import model.gameMap.GameMap
import model.room.Room
import utils.extensions.PositionExtension.{+, -}

object Minimap:

  extension (map: GameMap)
    def createMinimap(): List[MinimapElement] =
      val (rooms, initialRoom) = map.rooms.partition(_.name != map.initialRoom)
      extendMapping(initialRoom.head, rooms.toList, (0, 0)).toList

  private def extendMapping(room: Room, rooms: List[Room], position: Position): Set[MinimapElement] =
    val (connectedRooms, unmappedRooms) = rooms.partition(r => room.links.map(link => link.toRoom).contains(r.name))
    val mappedRooms = for
      roomsToMap <- connectedRooms
      newPosition = position + room.links
        .collectFirst {
          case link if link.toRoom == roomsToMap.name => link.direction.coordinates
        }
        .getOrElse((0, 0))
      mappedRoom <- extendMapping(roomsToMap, unmappedRooms, newPosition)
    yield mappedRoom
    mappedRooms.toSet + MinimapElement(room.name, position, room.links.map(_.direction).toSet, false)

