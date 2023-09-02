package model.cells.logic

import model.cells.{Cell, Item, ScoreCounter}
import model.game.ItemHolder
import utils.ConstantUtils

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

    /** Returns the value of the item in terms of score points */
    def mapItemToValue: Int = item match
      case Item.Coin  => ConstantUtils.CoinValue
      case Item.Bag   => ConstantUtils.BagValue
      case Item.Trunk => ConstantUtils.TrunkValue
      case _          => ConstantUtils.NotValuable

    /** Returns the value of the item in terms of score points */
    def updateScore(scoreCounter: ScoreCounter): ScoreCounter =
      scoreCounter.copy(score = scoreCounter.score + item.mapItemToValue)
