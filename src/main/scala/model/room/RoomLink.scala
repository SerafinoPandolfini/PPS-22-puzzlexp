package model.room

import model.cells.Direction

/**
 * the link between [[Room]]s
 * @param from the position of the room where the player is
 * @param direction the direction the player should move to change room
 * @param toRoom the name of the room reached
 * @param to the position in which the player will be after changing room
 */
case class RoomLink(from: (Int, Int), direction: Direction, toRoom: String, to: (Int, Int))
