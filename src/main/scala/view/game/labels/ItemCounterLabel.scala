package view.game.labels

import model.cells.properties.Item
import javax.swing.JLabel

/** a label for holding the counter of an [[Item]]
  * @param label
  *   the associated label
  * @param item
  *   the item it is counting
  * @param amount
  *   the quantity of the item
  */
case class ItemCounterLabel(label: JLabel, item: Item, amount: Int = 0) extends Label:

  override def updateLabel(newAmount: Int): ItemCounterLabel =
    label.setText(s"x${newAmount.toString}")
    copy(label, item, newAmount)
