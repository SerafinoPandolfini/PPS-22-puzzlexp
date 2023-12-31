package model.cells.traits

import model.cells.Cell

/** The mixin representing a cell with a hole that have a single-use, walkable cover
  */
trait Covered extends Cell:
  def cover: Boolean

  abstract override def isDeadly: Boolean =
    if cover then false
    else super.isDeadly
