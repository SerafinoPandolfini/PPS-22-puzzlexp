package model.cells

import model.cells.properties.Item
import model.cells.traits.Rock

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param broken
  *   if the rock is broken
  */
case class RockCell(position: Position, cellItem: Item = Item.Empty, broken: Boolean = false) extends Cell with Rock
