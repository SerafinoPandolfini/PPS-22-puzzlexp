package Model.Room

import Model.Cells.*
import Model.Room.Room.DummyCell
import Model.Cells.Extension.CellExtension.updateItem
import Exceptions.PlayerOutOfBoundsException
import Model.Cells.Extension.PositionExtension.+

/** @param name
  *   the name of the room
  * @param _cells
  *   the set of [[Cell]] of the room
  * @param links
  *   the set of [[RoomLink]] that connect the room to other rooms
  */
class Room(val name: String, private var _cells: Set[Cell], val links: Set[RoomLink]):

  /** getter for _cells
    * @return
    *   the set of cell of the room
    */
  def cells: Set[Cell] = _cells

  /** get a specific [[Cell]] from its position
    *
    * @param position
    *   the position of the cell
    * @return
    *   an optional of the required cell
    */
  def getCell(position: Position): Option[Cell] = _cells.find(_.position == position)

  /** update the _cells of the room using an immutable var
    *
    * @param updateSet
    *   the set of item update to apply to the room
    */
  def updateCellsItems(updateSet: Set[(Position, Item, Direction)]): Unit =
    for
      u <- updateSet
      updatedCells = getCell(u._1).getOrElse(DummyCell).updateItem(_cells, u._2, u._3)
    yield _cells = _cells.map(cell =>
      updatedCells.find(_.position == cell.position) match
        case Some(c) => c
        case None    => cell
    )

  /** check if the cell admit the player to walk on it
    *
    * @param cell
    *   the cell in which the player wants to move
    * @param dir
    *   the direction he is coming from
    * @return
    *   if the cell is walkable or not
    */
  private def isMovementValid(cell: Cell, dir: Direction): Boolean = cell.walkableState match
    case WalkableType.Walkable(b)          => b
    case WalkableType.DirectionWalkable(p) => p(dir)

  /** check if instead of moving the player there is some item to move instead or no movement at all
    *
    * @param position
    *   the player position
    * @param cell
    *   the cell in which the player wants to move
    * @param direction
    *   the direction the player is coming from
    * @return
    *   the new player position
    */
  private def checkItemMovement(position: Position, cell: Cell, direction: Direction): Option[Position] =
    cell.cellItem match
      case Item.Box =>
        // get the 2nd cell in line from the player and check if it exist and is empty,
        // otherwise return the original player position without updating any item
        val nextCell = getCell(cell.position + direction.coordinates)
        if nextCell.isEmpty || nextCell.get.cellItem != Item.Empty then return Some(position)
        // if the box is movable to the next cell handle the box movement
        if isMovementValid(nextCell.get, direction) then
          updateCellsItems(Set((cell.position, Item.Empty, direction), (nextCell.get.position, Item.Box, direction)))
        Some(position)
      case _ => Some(cell.position)

  /** @param currentPosition
    *   the player position
    * @param direction
    *   the direction in which the player wants to move
    * @return
    *   the new player position if its exist in this room or Option.empty if the cell do not exist
    */
  def playerMove(currentPosition: Position, direction: Direction): Option[Position] =
    getCell(currentPosition + direction.coordinates).flatMap { optionalCell =>
      if isMovementValid(optionalCell, direction) then  checkItemMovement(currentPosition, optionalCell, direction)
      else Some(currentPosition)
    }

  /** check if the cell the player is standing on is deadly
    *
    * @param currentPosition
    * @return
    *   if the cell is deadly or not or a PlayerOutOfBoundsException
    */
  def isPlayerDead(currentPosition: Position): Either[PlayerOutOfBoundsException, Boolean] =
    getCell(currentPosition) match
      case Some(c) => Right(c.isDeadly)
      case _       => Left(new PlayerOutOfBoundsException)

  /** represent the [[Room.cells]] as a stirng matrix
    * @param mapper
    *   optional mapper to add feature to the representation
    * @return
    *   the cells matrix
    */
  def cellsRepresentation(mapper: Cell => Option[String] = _ => Option.empty[String]): String =
    cells.toList.sorted
      .map(cell => mapper(cell).getOrElse(cellToString(cell)))
      .grouped(cells.maxBy(_.position._1).position._1 + 1)
      .map(row => row.mkString(" | "))
      .mkString("\n", "\n", "\n")

  /** map every [[Cell]] type to a two char string
    * @param cell
    *   the cell to map
    * @return
    *   the mapped cell
    */
  private def cellToString(cell: Cell): String =
    cell match
      case _: BasicCell               => "  "
      case _: WallCell                => "WL"
      case _: HoleCell                => "HL"
      case _: CoveredHoleCell         => "CH"
      case _: CliffCell               => "CL"
      case _: ButtonBlockCell         => "BB"
      case _: ButtonBlock             => "BT"
      case _: SwitchBlockCell         => "SB"
      case _: TeleportCell            => "TL"
      case _: TeleportDestinationCell => "TD"
      case _                          => "??"

object Room:

  /** the default width of a [[Room]]
    */
  val DefaultWidth: Int = 25

  /** the default height of a [[Room]]
    */
  val DefaultHeight: Int = 12

  /** a [[Cell]] provided to not make any update based on this
    */
  val DummyCell: Cell = WallCell(0, 0)

  /** map the position of the [[Item.Box]]es and of the player
    *
    * @param playerPos
    *   th player position
    * @return
    *   a set of optional string that are [[Option.empty]] when there is no player or box
    * @note
    *   the cell under the player and box will not be shown
    */
  def showPlayerAndBoxes(playerPos: Position): Cell => Option[String] = (cell: Cell) =>
    cell match
      case c if c.position == playerPos => Some("pl")
      case c if c.cellItem == Item.Box  => Some("bx")
      case c                            => None
