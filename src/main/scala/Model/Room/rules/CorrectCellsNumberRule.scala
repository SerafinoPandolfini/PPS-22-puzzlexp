package Model.Room.rules

import Model.Room.Room
import prologEngine.PrologEngine.{*, given}
import prologEngine.PrologConverter.*
import prologEngine.PrologEngine
import alice.tuprolog.{Struct, Term}

trait CorrectCellsNumberRule extends BaseRule:

  override def isRoomValid(room: Room): Boolean =
    val engine = PrologEngine("/prologTheory/non_repeating_cells_counter.pl")
    val input = Struct.of(
      "non_repeating_cells_counter",
      room.cells.map(convert(_)).toList,
      (Room.DefaultWidth * Room.DefaultHeight).toString
    )
    engine.solve(input) && super.isRoomValid(room)
