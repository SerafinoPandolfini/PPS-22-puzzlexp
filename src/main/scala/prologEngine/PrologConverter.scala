package prologEngine

import model.cells.{BasicCell, ButtonBlockCell, ButtonCell, Cell, WallCell}
import model.room.{Room, RoomLink}
import alice.tuprolog.{Struct, Term, Theory}
import model.cells.properties.Colorable
import utils.extensions.RoomCellsRepresentation.cellToString

object PrologConverter:
  val Separator = ","

  /** convert a [[cell]] into its prolog representation: c(cellName,position_x,position_y,{additional proprieties})
    * @param cell
    *   the cell to convert
    * @param properties
    *   eventual additional proprieties to add to the [[Cell]] representation, default add nothing
    * @return
    *   the prolog representation of the cell
    */
  def convertCellToProlog(cell: Cell, properties: Cell => String = noProperty): String =
    val cellString = cellToString(cell, true).toLowerCase
    val positionString = s"${cell.position._1}$Separator${cell.position._2}"
    val propertiesString = properties(cell)
    s"c($cellString$Separator$positionString$propertiesString)"

  def convertLinkToProlog(link: RoomLink): String = convertCellToProlog(BasicCell(link.from))

  /** add color property to prolog cells
    */
  val addColor: Cell => String = cell =>
    s"$Separator${(cell match
        case c: Cell with Colorable => c.color
        case _                      => "nil"
      ).toString.toLowerCase}"

  /** add the wall property if the cell is a wall
    */
  val isWall: Cell => String = cell =>
    s"$Separator${(cell match
        case _: WallCell => "wall"
        case _           => "nil"
      ).toLowerCase}"

  val noProperty: Cell => String = _ => ""
