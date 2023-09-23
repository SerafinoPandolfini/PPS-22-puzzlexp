package model.gameMap

import model.cells.Position
import model.cells.properties.Direction

import scala.collection.immutable.Set

/** the element that compose the [[Minimap]]
  * @param name
  *   the name of the corresponding [[model.room.Room]]
  * @param position
  *   the position inside the minimap
  * @param directions
  *   the transposition of the [[model.room.RoomLink]]
  * @param visited
  *   if the associated room has been visited by the player
  * @param existing
  *   if the associated room is real or a placeholder
  */
case class MinimapElement(
    name: String,
    position: Position,
    directions: Set[Direction],
    visited: Boolean,
    existing: Boolean
):

  /** visit the MinimapElement if it exist
   * @return a visited [[MinimapElement]]
   */
  def visit(): MinimapElement =
    if existing then copy(visited = true)
    else copy()
