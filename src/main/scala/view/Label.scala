package view

import javax.swing._
import model.cells.Item

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
