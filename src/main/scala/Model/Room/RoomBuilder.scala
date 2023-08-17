package Model.Room

import Model.Cells.{Cell, BasicCell, Item, WallCell, Position}

/** A builder for room
  */
class RoomBuilder(val RoomWidth: Int = Room.DefaultWidth, val RoomHeight: Int = Room.DefaultHeight):
  private var name: String = ""
  private var cells: Set[Cell] = Set.empty[Cell]
  private var links: Set[RoomLink] = Set.empty[RoomLink]

  // make sure there are no duplicate cells in the same position
  private def updateCells(c: Set[Cell]): Unit =
    cells = cells.filter(cell => c.find(_.position == cell.position).isEmpty) ++ c

  /** Add the room name
    *
    * @param roomName
    *   name of the room
    * @return
    *   this
    */
  def name(roomName: String): this.type =
    name = roomName
    this

  /** add a links to the room and create the cells for the linkings
    *
    * @param roomLinks
    *   the links to add to the room
    * @return
    *   this
    */
  def addLinks(roomLinks: RoomLink*): this.type =
    roomLinks.foreach(l =>
      updateCells(Set(BasicCell(l.from, Item.Empty)))
      links = links + l
    )
    this

  /** add a new cell to the room
    *
    * @param cell
    *   the new cell
    * @return
    *   this
    */
  def addCell(cell: Cell): this.type =
    updateCells(Set(cell))
    this

  /** add new cells or replace existing cells of the room with the new ones
    *
    * @param roomCells
    *   the new cell for the room
    * @return
    *   this
    */
  def addCells(roomCells: Set[Cell]): this.type =
    updateCells(roomCells)
    this

  /** create the border of the room so that every border cell is a wall
    *
    * @return
    *   this
    */
  def borderWalls(): this.type =
    val borderCells: Set[Cell] =
      ((0 until RoomWidth).flatMap(x => Set(WallCell((x, 0)), WallCell((x, RoomHeight - 1)))) ++
        (0 until RoomHeight).flatMap(y => Set(WallCell((0, y)), WallCell((RoomWidth - 1, y))))).toSet
    updateCells(borderCells)
    this

  /** create a rectangle of wall cell
    *
    * @param position
    *   north-west angle of the rectangle
    * @param width
    *   horizontal size
    * @param height
    *   vertical size
    * @return
    *   this
    * @note
    *   providing non-positive number for width and height will not produce any cell
    */
  def wallRectangle(position: Position, width: Int, height: Int): this.type =
    val rectangle =
      for
        x <- position._1 until position._1 + width
        y <- position._2 until position._2 + height
      yield WallCell((x, y))
    updateCells(rectangle.toSet)
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
