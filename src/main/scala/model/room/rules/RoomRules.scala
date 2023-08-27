package model.room.rules

class RoomRules
    extends BaseRoomRule
    with ValidPositionsRule
    with CorrectCellsNumberRule
    with BorderCellsRule
    with TeleportCellsRule
    with PressureCellsRule
    with ButtonCellsRule
