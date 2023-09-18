package model.cells

import model.cells.properties.Item
import model.cells.traits.{CoveredHole, Hole}

/** @param position
  *   The position of the cell in the room
  * @param cellItem
  *   The item on the cell
  * @param cover
  *   if there is a cover on the hole
  * @param filled
  *   if the hole is filled
  */
case class CoveredHoleCell(
    position: Position,
    cellItem: Item = Item.Empty,
    cover: Boolean = true,
    filled: Boolean = false
) extends Cell
    with Hole
    with CoveredHole
