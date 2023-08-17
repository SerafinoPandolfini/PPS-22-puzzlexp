package Model.Room

import Model.Cells.{Cell, BasicCell, Item}

/** A builder for room
  */
class RoomBuilder(val RoomWidth: Int = Room.DefaultWidth, val RoomHeight: Int = Room.DefaultHeight):
  private var name: String = ""
  private var cells: Set[Cell] = Set.empty[Cell]
  private var links: Set[RoomLink] = Set.empty[RoomLink]

  def standardize: this.type =
    for
      x <- 0 until RoomWidth
      y <- 0 until RoomHeight
    yield if !cells.exists(_.position == (x, y)) then cells += BasicCell((x, y), Item.Empty)
    this

  /** @return
    *   the room created by the builder
    * @note
    *   return a room of basic cells if the room does not contain any cell
    */
  def build: Room =
    if cells.isEmpty then standardize
    new Room(name, cells, links)
