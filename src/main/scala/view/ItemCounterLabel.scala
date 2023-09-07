package view

import javax.swing._
import model.cells.Item

case class ItemCounterLabel(label: JLabel, item: Item, amount: Int = 0) extends Label:

  override def updateLabel(newAmount: Int): ItemCounterLabel =
    label.setText("x" concat newAmount.toString)
    copy(label, item, newAmount)



