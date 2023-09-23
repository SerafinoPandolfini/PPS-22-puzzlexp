package model.gameMap

import model.cells.Position
import model.gameMap.GameMap
import model.room.Room
import utils.extensions.PositionExtension.{+, -}

object Minimap:

  extension (map: GameMap)

    def createMinimap(): List[MinimapElement] =
      val (rooms, initialRoom) = map.rooms.partition(_.name != map.initialRoom)
      val minimapElements = extendMapping(initialRoom.head, rooms.toList, (0, 0))
      val correctionValue = standardizePosition(minimapElements)
      minimapElements.map(e => e.copy(position = e.position - correctionValue)).toList.sorted(MinimapOrdering)

  given MinimapOrdering: Ordering[MinimapElement] = Ordering.by(e => (e.position._2, e.position._1))

  private def extendMapping(room: Room, rooms: List[Room], position: Position): Set[MinimapElement] =
    val (connectedRooms, unmappedRooms) = rooms.partition(r => room.links.map(link => link.toRoom).contains(r.name))
    val mappedRooms = for
      roomsToMap <- connectedRooms
      newPosition = position + room.links
        .collectFirst {
          case link if link.toRoom == roomsToMap.name => link.direction.coordinates
        }
        .getOrElse((0,0))
      mappedRoom <- extendMapping(roomsToMap, unmappedRooms, newPosition)
    yield mappedRoom
    mappedRooms.toSet + MinimapElement(room.name, position, room.links.map(_.direction).toSet, false)

  private def standardizePosition(minimapElements: Iterable[MinimapElement]): (Int, Int) =
    minimapElements.foldLeft((0, 0)) { case ((minX, minY), element) =>
      (Math.min(minX, element.position._1), Math.min(minY, element.position._2))
    }
