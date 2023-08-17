package Model.Room

import Model.Cells.*

class Room(val name: String, private var _cells: Set[Cell], val links: Set[RoomLink]):

  /** getter for _cells
    * @return
    *   the set of cell of the room
    */
  def cells: Set[Cell] = _cells

object Room:

  val DefaultWidth: Int = 25

  val DefaultHeight: Int = 12
