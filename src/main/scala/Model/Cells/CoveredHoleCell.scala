package Model.Cells

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
    with CoveredHole:

  override def update(item: Item): CoveredHoleCell = item match
    case Item.Box =>
      if filled then CoveredHoleCell(position, item, false, true)
      else CoveredHoleCell(position, cellItem, false, true)
    case _ => this

  /** Breaks the cover of the hole */
  def brokeCover(): CoveredHoleCell = CoveredHoleCell(position, cellItem, false)
