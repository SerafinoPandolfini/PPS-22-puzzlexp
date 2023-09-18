package view.game.labels

import model.cells.properties.Item
import javax.swing.*

case class ItemCounterLabel(label: JLabel, item: Item, amount: Int = 0) extends Label:

  override def updateLabel(newAmount: Int): ItemCounterLabel =
    label.setText("x" concat newAmount.toString)
    copy(label, item, newAmount)



