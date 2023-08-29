package model.cells

trait TreasureType:
  self: Treasure =>
  override case class TreasureType(size: TreasureSize)
