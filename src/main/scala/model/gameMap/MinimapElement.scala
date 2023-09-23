package model.gameMap

import model.cells.Position
import model.cells.properties.Direction

import scala.collection.immutable.Set

case class MinimapElement(name: String, position: Position, directions: Set[Direction], visited: Boolean)
