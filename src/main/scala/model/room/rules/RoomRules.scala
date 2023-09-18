package model.room.rules

import model.cells.Cell
import model.room.Room
import prologEngine.PrologEngine
import alice.tuprolog.{Struct, Term}
import prologEngine.PrologConverter.convertCellToProlog
import prologEngine.PrologEngine.{*, given}

/** all the standard rules for a [[Room]]
 */
class RoomRules
    extends BaseRoomRule
    with ValidPositionsRule
    with CorrectCellsNumberRule
    with BorderCellsRule
    with TeleportCellsRule
    with PressureCellsRule
    with ButtonCellsRule
