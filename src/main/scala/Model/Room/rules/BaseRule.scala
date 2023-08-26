package Model.Room.rules

import Model.Room.Room

trait BaseRule:

  def isRoomValid(room: Room): Boolean = true

