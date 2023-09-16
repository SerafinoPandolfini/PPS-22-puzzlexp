package model.room.rules

import model.room.RoomImpl

/** common values used by the rules of [[Room]] */
object CommonGroundTerm:
  val WidthLimit: Int = RoomImpl.DefaultWidth - 1
  val HeightLimit: Int = RoomImpl.DefaultHeight - 1
