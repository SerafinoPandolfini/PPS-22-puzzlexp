package model.cells

case class TreasureCell(
    position: Position,
    items: List[Item],
    size: TreasureSize,
    cellItem: Item = Item.Empty,
    open: Boolean = false
) extends Cell
    with Treasure

//with TreasureAndMoneyType
