package utils.extensions

import model.cells.*
import model.cells.properties.Item
import model.room.Room

object RoomCellsRepresentation:

  /** map the position of the [[Item.Box]]es and of the player
   *
   * @param playerPos
   * th player position
   * @return
   * a set of optional string that are [[Option.empty]] when there is no player or box
   * @note
   * the cell under the player and box will not be shown
   */
  def showPlayerAndBoxes(playerPos: Position): Cell => Option[String] = (cell: Cell) =>
    cell match
      case c if c.position == playerPos => Option("pl")
      case c if c.cellItem == Item.Box => Option("bx")
      case _ => None

  /** map every [[Cell]] type to a two char string
   *
   * @param cell
   * the cell to map
   * @param showBasic
   * if he need to map the [[BasicCell]] or not
   * @return
   * the mapped cell
   */
  def cellToString(cell: Cell, showBasic: Boolean = false): String =
    cell match
      case _: BasicCell => if showBasic then "BS" else "  "
      case _: WallCell => "WL"
      case _: HoleCell => "HL"
      case _: CoveredHoleCell => "CH"
      case _: CliffCell => "CL"
      case _: ButtonBlockCell => "BB"
      case _: ButtonCell => "BT"
      case _: PressurePlateBlockCell => "PB"
      case _: PressurePlateCell => "PP"
      case _: TeleportCell => "TL"
      case _: TeleportDestinationCell => "TD"
      case _: LockCell => "LK"
      case _: RockCell => "RK"
      case _: PlantCell => "PL"
      case _ => "??"

  extension (r: Room)

    /** represent the [[Room.cells]] as a stirng matrix
     *
     * @param mapper
     * optional mapper to add feature to the representation
     * @return
     * the cells matrix
     */
    def cellsRepresentation(mapper: Cell => Option[String] = _ => Option.empty[String]): String =
      r.cells.toList.sorted
        .map(cell => mapper(cell).getOrElse(cellToString(cell)))
        .grouped(r.cells.maxBy(_.position._1).position._1 + 1)
        .map(row => row.mkString(" | "))
        .mkString("\n", "\n", "\n")

