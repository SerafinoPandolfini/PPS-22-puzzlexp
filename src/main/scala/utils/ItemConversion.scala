package utils

import ConstantUtils.Point2D
import model.cells.Item

/** these given are a mapping from item to int/string */
object ItemConversion:
  /** Returns the value of the item in terms of score points */
  given Conversion[Item, Int] = _ match
    case Item.Coin  => ConstantUtils.CoinValue
    case Item.Bag   => ConstantUtils.BagValue
    case Item.Trunk => ConstantUtils.TrunkValue
    case _          => ConstantUtils.NotValuable

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
