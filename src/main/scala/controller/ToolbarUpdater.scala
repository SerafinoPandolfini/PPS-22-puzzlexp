package controller

import model.cells.logic.TreasureExtension.*
import controller.GameController.view
import model.cells.Item
import model.game.CurrentGame

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
      if item.isTreasure then score = score + item.mapItemToValue * count
    }
    view.updateScore(score)
