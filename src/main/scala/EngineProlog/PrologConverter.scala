package EngineProlog

import Model.Cells.{ButtonBlockCell, Cell}
import Model.Room.Room
//import Engine.PrologEngine.{*, given}
import alice.tuprolog.{Struct, Term, Theory}

object PrologConverter:
  val Separator = ","

  def convert(cell: Cell, properties: Cell => String = _ => ""): String =
    val cellString = Room.cellToString(cell, true).toLowerCase
    val positionString = s"${cell.position._1}$Separator${cell.position._2}"
    val propertiesString = properties(cell)

    s"c($cellString$Separator$positionString$propertiesString)"

  /** aggiunge la proprietà color alle celle costruite
    */
  val addColor: Cell => String = cell =>
    Separator + (cell match
      case c: ButtonBlockCell => c.color.toString.toLowerCase
      case _                  => "nil"
    )
