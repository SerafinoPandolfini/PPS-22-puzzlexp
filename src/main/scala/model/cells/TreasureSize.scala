package model.cells

/** Enumeration representing the value of the money, they increase the score of the player */
enum TreasureSize(_score: Int):
  case Coin extends TreasureSize(10)
  case Bag extends TreasureSize(20)
  case Trunk extends TreasureSize(50)

  /** @return
    *   the int value bound its name
    */
  val score: Int = _score
