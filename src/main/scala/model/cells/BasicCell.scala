package model.cells

import model.cells.properties.Item

/** The classic, walkable cell, with no peculiar effects
  *
  * @param position
  *   the position of the cell in the room
  * @param cellItem
  *   the item on the cell
  */
case class BasicCell(position: Position, cellItem: Item = Item.Empty) extends Cell
