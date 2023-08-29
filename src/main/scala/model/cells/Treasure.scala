package model.cells
import model.cells.WalkableType.{DirectionWalkable, Walkable}

/** The mixin representing a treasure. It contains money and maybe some items */
trait Treasure extends Cell:
  /*/** abstract types, members of Treasure */
  type MoneyType*/

  /** The scores present in the treasure */
  def size: TreasureSize

  /** The list of possible items present in the treasure */
  def items: List[Item]

  /** if the treasure is open */
  def open: Boolean

  /** the player can always walk through the treasure */
  abstract override def walkableState: WalkableType = Walkable(true)
