package Model.Room

import Model.Cells.*
import Model.Room.Room.DummyCell
import Model.Cells.Extension.CellExtension.updateItem

class Room(val name: String, private var _cells: Set[Cell], val links: Set[RoomLink]):

  /** getter for _cells
    * @return
    *   the set of cell of the room
    */
  def cells: Set[Cell] = _cells

  /** get a specific cell from its position
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

  def cellsRepresentation: String =
    val rowSize = cells.maxBy(_.position._1).position._1 + 1
    println(rowSize)
    cells.toList.sorted
      .map(cellToString(_))
      .grouped(rowSize)
      .map(row => row.mkString(" | "))
      .mkString("\n", "\n", "\n")

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

  /** the default width of a room
    */
  val DefaultWidth: Int = 25

  /** the default height of a room
    */
  val DefaultHeight: Int = 12

  /** a cell provided to not make any update based on this
    */
  val DummyCell: Cell = WallCell(0, 0)
