package model.room

import model.cells.{BasicCell, Cell, Item, Position, WallCell}
import model.room.Room

import scala.annotation.targetName

/** A builder for [[Room]]
  */
class RoomBuilder(val RoomWidth: Int = RoomImpl.DefaultWidth, val RoomHeight: Int = RoomImpl.DefaultHeight):
  private var name: String = ""
  private var cells: Set[Cell] = Set.empty[Cell]
  private var links: Set[RoomLink] = Set.empty[RoomLink]
  private var validity = false

  /** make sure there are no duplicate cells in the same position */
  private def updateCells(c: Set[Cell]): Unit =
    cells = cells.filter(cell => !c.exists(_.position == cell.position)) ++ c

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
    roomLinks.foreach(l => updateCells(Set(BasicCell(l.from, Item.Empty))))
    links = links ++ roomLinks
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

  /** Alias for [[RoomBuilder.addCell]]. */
  @targetName("addCellAlias")
  def +(cell: Cell): this.type = addCell(cell)

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

  /** Alias for [[RoomBuilder.addCells]] */
  @targetName("addCellsAlias")
  def ++(roomCells: Set[Cell]): this.type = addCells(roomCells)

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

  /** Alias for [[RoomBuilder.borderWalls]] */
  @targetName("borderWallsAlias")
  def ##(): this.type = borderWalls()

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

  /** Alias for [[RoomBuilder.wallRectangle]] */
  @targetName("wallRectangleAlias")
  def ||(position: Position, width: Int, height: Int): this.type = wallRectangle(position, width, height)

  /** remove the cells outside the room border and fill the inside of the room with basic cells
    *
    * @return
    *   this
    */
  def standardize: this.type =
    cells = cells.filter(cell =>
      cell.position._1 >= 0 && cell.position._1 < RoomWidth &&
        cell.position._2 >= 0 && cell.position._2 < RoomHeight
    )
    for
      x <- 0 until RoomWidth
      y <- 0 until RoomHeight
    yield if !cells.exists(_.position == (x, y)) then cells += BasicCell((x, y), Item.Empty)
    this

  /** Alias for [[RoomBuilder.standardize]] */
  @targetName("standardizeAlias")
  def !! : this.type = standardize

  /** add valitation to the room
    * @return
    *   this
    */
  def requestValidation: this.type =
    validity = true
    this

  /** @return
    *   the room created by the builder
    * @note
    *   return a room of basic cells if the room does not contain any cell
    */
  def build: Room =
    if cells.isEmpty then standardize
    Room(name, cells, links, validity)
