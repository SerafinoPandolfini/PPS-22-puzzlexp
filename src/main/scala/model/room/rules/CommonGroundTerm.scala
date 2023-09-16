package model.room.rules

import model.room.Room

/** common values used by the rules of [[Room]] */
object CommonGroundTerm:
  val WidthLimit: Int = Room.DefaultWidth - 1
  val HeightLimit: Int = Room.DefaultHeight - 1
