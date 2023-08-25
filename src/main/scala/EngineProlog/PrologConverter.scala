package EngineProlog

import Model.Cells.{ButtonBlockCell, Cell}
import Model.Room.Room
//import Engine.PrologEngine.{*, given}
import alice.tuprolog.{Struct, Term, Theory}

object PrologConverter:
  val Separator = ","

  /**
   * convert a [[cell]] into its prolog representation: c(cellName,position_x,position_y,{additional proprieties})
   * @param cell the cell to convert
   * @param properties eventual additional proprieties to add to the [[Cell]] representation, default add nothing
   * @return the prolog representation of the cell
   */
  def convert(cell: Cell, properties: Cell => String = _ => ""): String =
    val cellString = Room.cellToString(cell, true).toLowerCase
    val positionString = s"${cell.position._1}$Separator${cell.position._2}"
    val propertiesString = properties(cell)
    s"c($cellString$Separator$positionString$propertiesString)"
