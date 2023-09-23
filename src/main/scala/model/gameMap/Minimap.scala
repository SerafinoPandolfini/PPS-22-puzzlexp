package model.gameMap

import model.cells.Position
import model.cells.properties.Direction
import model.gameMap.GameMap
import model.room.Room
import utils.extensions.PositionExtension.{+, -}

/** the minimap of the [[Room]]s of the played [[GameMap]]
  */
object Minimap:

  extension (map: GameMap)
    /** create the associated minimap as a list of [[MinimapElement]]s
      */
    def createMinimap(): List[MinimapElement] =
      val (rooms, initialRoom) = map.rooms.partition(_.name != map.initialRoom)
      val minimapElements = extendMapping(initialRoom.head, rooms.toList)
      val correctionValue = standardizePosition(minimapElements)
      fillGaps(
        minimapElements
          .map(e => e.copy(position = e.position - correctionValue))
          .toList
      ).sorted(MinimapOrdering)

  /** the order of the minimap
   * @return an ordered [[List]] of [[MinimapElement]] for rows
   */
  given MinimapOrdering: Ordering[MinimapElement] = Ordering.by(e => (e.position._2, e.position._1))

  /** produces a transposition of the [[Room]]s into [[MinimapElement]]s
   * @param room the [[GameMap.initialRoom]] where the player starts
   * @param rooms all the other [[Room]]s in [[GameMap]]
   * @param position the starting [[Position]] for the mapping
   * @return the partial [[Minimap]]
   */
  private def extendMapping(room: Room, rooms: List[Room], position: Position = (0, 0)): Set[MinimapElement] =
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
    mappedRooms.toSet + MinimapElement(room.name, position, room.links.map(_.direction).toSet, false, true)

  /** search the minimum values of the mapping for fixing [[MinimapElement]]s' [[Position]]s
   * @param minimapElements the elements of the minimap
   * @return the value to subtract to all [[MinimapElement]]s
   */
  private def standardizePosition(minimapElements: Iterable[MinimapElement]): (Int, Int) =
    minimapElements.foldLeft((0, 0)) { case ((minX, minY), element) =>
      (Math.min(minX, element.position._1), Math.min(minY, element.position._2))
    }

  /** search for missing [[MinimapElement]]s and produces placeholders for filling the gaps
   * @param minimap the incomplete minimap 
   * @return the new minimap completed with placeholders
   */
  private def fillGaps(minimap: List[MinimapElement]): List[MinimapElement] =
    val (maxX, maxY): Position = minimap.foldLeft((Int.MinValue, Int.MinValue)) { case ((maxX, maxY), element) =>
      (math.max(maxX, element.position._1), math.max(maxY, element.position._2))
    }
    val elementsPositions = minimap.map(_.position)
    val placeholders = for
      x <- 0 to maxX
      y <- 0 to maxY
      if !elementsPositions.contains(x, y)
    yield MinimapElement("", (x, y), Set.empty[Direction], false, false)
    minimap ++ placeholders
