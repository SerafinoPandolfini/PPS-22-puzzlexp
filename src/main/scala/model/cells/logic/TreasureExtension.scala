package model.cells.logic

import model.cells.TreasureCell
import model.cells.TreasureSize.*

object TreasureExtension:
  /** constants that indicate the maximum items in Coin and Bag treasure */
  val MaxItemsInCoinTreasure: Int = 0
  val MaxItemsInBagTreasure: Int = 1

  /** extension for adding new methods to the treasure cell
    */
  extension (cell: TreasureCell)
    /** Returns the number of items in the treasure */
    private def numberOfItems: Int = cell.items.length

    /** Returns the number of items in the treasure */
    def checkItems: Boolean = cell.size match
      case Coin => cell.numberOfItems == MaxItemsInCoinTreasure
      case Bag  => cell.numberOfItems <= MaxItemsInBagTreasure
      case _    => true
