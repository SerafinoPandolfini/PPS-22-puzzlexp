package model.cells.logic

import model.cells.{Cell, Item}
import model.game.ItemHolder
import utils.ConstantUtils
import utils.ItemConversion.given_Conversion_Item_Int

import java.security.KeyStore.TrustedCertificateEntry

object TreasureExtension:
  /** extension for adding new methods to the treasure item
    */
  extension (item: Item)
    /** Returns true if the item is an treasure */
    def isTreasure: Boolean = item match
      case Item.Coin  => true
      case Item.Bag   => true
      case Item.Trunk => true
      case _          => false
