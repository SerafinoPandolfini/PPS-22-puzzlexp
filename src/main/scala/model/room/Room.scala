package model.room

import model.cells.*
import model.cells.properties.{Direction, Item}

import scala.util.Try
import model.room.rules.RoomRules

/** the concept of a grid of [[Cell]]s with a name and [[RoomLink]]s to other rooms*/
trait Room:

  /** @return
    *   the room name
    */
  def name: String

  /** @return
    *   the [[RoomLink]]s of this room
    */
  def links: Set[RoomLink]

  /** @return
    *   the set of cell of the room
    */
  def cells: Set[Cell]

  /** get a specific [[Cell]] from its position
    *
    * @param position
    *   the position of the cell
    * @return
    *   an optional of the required cell
    */
  def getCell(position: Position): Option[Cell]

  /** update the cells of the room
    *
    * @param updateSet
    *   the set of item update to apply to the room
    */
  def updateCellsItems(updateSet: Set[(Position, Item, Direction)]): Unit

  def updateCells(cells: Set[Cell]): Unit

  /** check if the cell admit the player to walk on it
    *
    * @param cell
    *   the cell in which the player wants to move
    * @param dir
    *   the direction he is coming from
    * @return
    *   if the cell is walkable or not
    */
  def isMovementValid(cell: Cell, dir: Direction): Boolean

  /** @param currentPosition
    *   the player position
    * @param direction
    *   the direction in which the player wants to move
    * @return
    *   the new player position if its exist in this room or Option.empty if the cell do not exist
    */
  def playerMove(currentPosition: Position, direction: Direction): Option[Position]

  /** Check the consequenses of the movement of the player updating the room cells
    * @param previous
    *   the [[Position]] of the cell from which the movement is made
    * @param next
    *   the [[Position]] of the cell to which the movement is made
    * @return
    *   the [[Position]] in which the player should be or a [[PlayerOutOfBoundsException]] if one of the input data is
    *   not in the room
    */
  def checkMovementConsequences(previous: Position, next: Position): Try[Position]

  /** check if the cell the player is standing on is deadly
    *
    * @param currentPosition
    *   the [[Position]] of the player
    * @return
    *   if the cell is deadly or not or a PlayerOutOfBoundsException
    */
  def isPlayerDead(currentPosition: Position): Try[Boolean]

  /** get a copy of the current room
    * @return
    *   the copy of the room
    */
  def copy(): Room

object Room:

  def apply(roomName: String, roomCells: Set[Cell], roomLinks: Set[RoomLink], validity: Boolean = false): Room =
    val room = new RoomImpl(roomName, roomCells, roomLinks)
    if validity then println(RoomRules().checkRoomValidity(room))
    room
