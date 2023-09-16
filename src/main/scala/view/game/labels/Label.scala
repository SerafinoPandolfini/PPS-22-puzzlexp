package view.game.labels

import model.cells.properties.Item
import javax.swing.*

trait Label:

  /**
   * 
   * @return the associated [[JLabel]]
   */
  def label: JLabel

  /**
   * 
   * @return the [[Item]] associated to the label
   */
  def item: Item

  /**
   * 
   * @return the amount of [[Item]] related to this label
   */
  def amount: Int

  /** recreate the label 
   * @param amount the new amount of the label
   * @return the new label
   */
  def updateLabel(amount: Int): Label
