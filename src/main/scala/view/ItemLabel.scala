package view

import javax.swing._
import model.cells.Item

case class ItemLabel(label: JLabel, item: Item, amount: Int = 0, itemPath: String) extends Label:

  override def updateLabel(newAmount: Int): ItemLabel =
    if newAmount > 0 then label.setIcon(ImageIcon(itemPath))
    copy(label, item, newAmount, itemPath)
