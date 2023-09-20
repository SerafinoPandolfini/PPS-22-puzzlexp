package model.cells.properties

import model.cells.properties.Color

/** A mixin that adds a color field
  */
trait Colorable:

  /** the color of the colorable element
    */
  def color: Color
