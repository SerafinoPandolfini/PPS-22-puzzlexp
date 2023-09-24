package model.room.rules

import model.cells.Cell
import model.room.Room

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
