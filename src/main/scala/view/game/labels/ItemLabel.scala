package view.game.labels

import model.cells.properties.Item

import java.net.URL
import javax.swing.*

case class ItemLabel(label: JLabel, item: Item, amount: Int = 0, itemPath: URL) extends Label:

  override def updateLabel(newAmount: Int): ItemLabel =
    if newAmount > 0 then label.setIcon(ImageIcon(itemPath))
    copy(label, item, newAmount, itemPath)
