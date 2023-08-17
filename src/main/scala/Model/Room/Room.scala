package Model.Room

import Model.Cells.*

class Room(val name: String, private var _cells: Set[Cell], val links: Set[RoomLink]):

  /** getter for _cells
    * @return
    *   the set of cell of the room
    */
  def cells: Set[Cell] = _cells

  /** get a specific cell from its position
   *
   * @param position
   * the position of the cell
   * @return
   * an optional of the required cell
   */
  def getCell(position: Position): Option[Cell] = _cells.find(_.position == position)
  
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
      case _: BasicCell => "  "
      case _: WallCell => "WL"
      case _: HoleCell => "HL"
      case _: CoveredHoleCell => "CH"
      case _: CliffCell => "CL"
      case _: ButtonBlockCell => "BB"
      case _: ButtonBlock => "BT"
      case _: SwitchBlockCell => "SB"
      case _: TeleportCell => "TL"
      case _: TeleportDestinationCell => "TD"
      case _ => "??"

object Room:

  val DefaultWidth: Int = 25

  val DefaultHeight: Int = 12
