package Model.Cells

/** The mixin representing an obstacle that is walkable and deadly, but can be filled with a box item */
trait Hole extends Cell:
  private[this] var filled = false

  abstract override def isDeadly: Boolean = !filled

  abstract override def update(i: Item): Unit = i match
    case Item.Box =>
      if filled then
        super.update(i)
      else
        filled = true
        _cellItem = Item.Empty
    case _ => _cellItem = Item.Empty
