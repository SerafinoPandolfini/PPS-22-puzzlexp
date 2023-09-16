package utils.givens

import model.cells.properties.Item
import utils.constants.GraphicManager.Point2D

/** these given are a mapping from item to int/string */
object ItemConversion:

  private val CoinValue: Int = 10
  private val BagValue: Int = 20
  private val TrunkValue: Int = 50
  private val NotValuable: Int = 0

  /** Returns the value of the item in terms of score points */
  given Conversion[Item, Int] = _ match
    case Item.Coin  => CoinValue
    case Item.Bag   => BagValue
    case Item.Trunk => TrunkValue
    case _          => NotValuable

  /** map each item to the related string */
  given Conversion[Item, String] = _ match
    case i if i == Item.Box   => "BOX"
    case i if i == Item.Axe   => "AXE"
    case i if i == Item.Key   => "KEY"
    case i if i == Item.Pick  => "PICK"
    case i if i == Item.Coin  => "COIN"
    case i if i == Item.Bag   => "BAG"
    case i if i == Item.Trunk => "TRUNK"
    case _                    => "??"
