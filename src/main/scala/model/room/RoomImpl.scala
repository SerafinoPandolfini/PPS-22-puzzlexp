package model.room

import exceptions.PlayerOutOfBoundsException
import model.cells.{Cell, Direction, Item, Position, WalkableType, WallCell}
import model.cells.logic.CellExtension.*
import utils.PositionExtension.+
import model.room.RoomImpl.DummyCell

import scala.util.{Failure, Success, Try}

/** the standard implementation for [[Room]]*/
private[room] class RoomImpl(val name: String, private var _cells: Set[Cell], val links: Set[RoomLink]) extends Room :

  override def cells: Set[Cell] = _cells

  override def getCell(position: Position): Option[Cell] = _cells.find(_.position == position)

  override def updateCellsItems(updateSet: Set[(Position, Item, Direction)]): Unit =
    for
      (position, item, direction) <- updateSet
      updatedCells = getCell(position).getOrElse(DummyCell).updateItem(_cells, item, direction)
    yield updateCells(updatedCells)

  override def updateCells(cells: Set[Cell]): Unit =
    _cells = _cells.map(cell =>
      cells.find(_.position == cell.position) match
        case Some(c) => c
        case None => cell
    )

  override def isMovementValid(cell: Cell, dir: Direction): Boolean = cell.walkableState match
    case WalkableType.Walkable(b) => b
    case WalkableType.DirectionWalkable(p) => p(dir)

  /** check if instead of moving the player there is some item to move instead or no movement at all
   *
   * @param position
   * the player position
   * @param cell
   * the cell in which the player wants to move
   * @param direction
   * the direction the player is coming from
   * @return
   * the new player position
   */
  private def checkItemMovement(position: Position, cell: Cell, direction: Direction): Option[Position] =
    cell.cellItem match
      case Item.Box =>
        val nextCell = getCell(cell.position + direction.coordinates)
        if nextCell.isEmpty || nextCell.get.cellItem != Item.Empty then return Option(position)
        if isMovementValid(nextCell.get, direction) then
          updateCellsItems(Set((cell.position, Item.Empty, direction), (nextCell.get.position, Item.Box, direction)))
        Option(position)
      case _ => Option(cell.position)

  override def playerMove(currentPosition: Position, direction: Direction): Option[Position] =
    getCell(currentPosition + direction.coordinates).flatMap { optionalCell =>
      if isMovementValid(optionalCell, direction) then checkItemMovement(currentPosition, optionalCell, direction)
      else Option(currentPosition)
    }

  override def checkMovementConsequences(previous: Position, next: Position): Try[Position] =
    getCell(previous) match
      case Some(v) =>
        getCell(next) match
          case Some(value) =>
            updateCells(v.moveOut(_cells))
            val (newSet, destinationPosition) = value.moveIn(_cells)
            updateCells(newSet)
            Success(destinationPosition)
          case None => Failure(new PlayerOutOfBoundsException)
      case None => Failure(new PlayerOutOfBoundsException)

  override def isPlayerDead(currentPosition: Position): Try[Boolean] =
    getCell(currentPosition) match
      case Some(c) => Success(c.isDeadly)
      case _ => Failure(PlayerOutOfBoundsException())
  
  override def copy(): Room = Room(name, cells, links)

object RoomImpl:
  /** the default width of a [[Room]]
   */
  val DefaultWidth: Int = 25

  /** the default height of a [[Room]]
   */
  val DefaultHeight: Int = 13

  /** a [[Cell]] provided to not make any update based on this
   */
  val DummyCell: Cell = WallCell((0, 0))