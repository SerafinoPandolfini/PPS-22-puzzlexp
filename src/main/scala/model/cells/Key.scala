package model.cells

/** The mixin representing a key. This cell has a key. If the cell is crossed, the key is gathered in the
  * [[model.room.ItemHolder]]
  */
trait Key extends Cell:
  /** if the key is present */
  def isKeyPresent: Boolean

  /** if the key is present, it */
  def key: Item
