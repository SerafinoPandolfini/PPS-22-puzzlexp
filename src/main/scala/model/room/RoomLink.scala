package model.room

import model.cells.properties.Direction
import model.cells.Position

/** the link between [[Room]]s
  * @param from
  *   the position of the room where the player is
  * @param direction
  *   the direction the player should move to change room
  * @param toRoom
  *   the name of the room reached
  * @param to
  *   the position in which the player will be after changing room
  */
case class RoomLink(from: Position, direction: Direction, toRoom: String, to: Position)
