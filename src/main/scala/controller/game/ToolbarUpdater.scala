package controller.game

import controller.game.GameController.view
import model.cells.logic.TreasureExtension.*
import model.cells.properties.Item
import model.game.CurrentGame
import utils.givens.ItemConversion.given_Conversion_Item_Int

object ToolbarUpdater:

  /** Updates the toolbar's label
    */
  private[controller] def updateToolbarLabels(): Unit =
    val itemCounts: Map[Item, Int] = Item.values.map(item => item -> 0).toMap ++ CurrentGame.itemHolder.itemOwned
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap
    var score = 0
    itemCounts.foreach { case (item, count) =>
      view.updateItemLabel(item, count)
      if item.isTreasure then score = score + item * count
    }
    view.updateScore(score)
