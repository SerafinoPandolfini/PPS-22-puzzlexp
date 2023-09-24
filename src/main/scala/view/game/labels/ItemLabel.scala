package view.game.labels

import model.cells.properties.Item

import java.net.URL
import javax.swing.{JLabel, ImageIcon}

/** a label for storing a specific single [[Item]]
  *
  * @param label
  *   the associated label
  * @param item
  *   the item it is holding
  * @param amount
  *   the quantity of that item
  * @param itemPath
  *   the path for the item image
  */
case class ItemLabel(label: JLabel, item: Item, amount: Int = 0, itemPath: URL) extends Label:

  override def updateLabel(newAmount: Int): ItemLabel =
    if newAmount > 0 then label.setIcon(ImageIcon(itemPath))
    copy(label, item, newAmount, itemPath)
