package model.room.rules

import model.room.Room

trait BaseRule:

  def isRoomValid(room: Room): Boolean = true

