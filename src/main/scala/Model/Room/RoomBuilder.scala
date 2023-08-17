package Model.Room

import Model.Cells.{Cell, BasicCell, Item}

/** A builder for room
  */
class RoomBuilder(val RoomWidth: Int = Room.DefaultWidth, val RoomHeight: Int = Room.DefaultHeight):
  private var name: String = ""
  private var cells: Set[Cell] = Set.empty[Cell]
  private var links: Set[RoomLink] = Set.empty[RoomLink]

  /** add a new cell to the room
    *
    * @param cell
    *   the new cell
    * @return
    *   this
    */
  def addCell(cell: Cell): this.type =
    addCells(Set(cell))
    this

  /** add new cells or replace existing cells of the room with the new ones
    *
    * @param roomCells
    *   the new cell for the room
    * @return
    *   this
    */
  def addCells(roomCells: Set[Cell]): this.type =
    cells = cells.filter(cell => roomCells.find(_.position == cell.position).isEmpty) ++ roomCells
    this

  /** remove the cells outside the room border and fill the inside of the room with basic cells
    *
    * @return
    *   this
    */
  def standardize: this.type =
    // Remove cells outside the border
    cells = cells.filter(cell =>
      cell.position._1 >= 0 && cell.position._1 < RoomWidth &&
        cell.position._2 >= 0 && cell.position._2 < RoomHeight
    )
    // Fill empty positions with BasicCell
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
