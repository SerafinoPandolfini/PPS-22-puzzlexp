package utils.constants

import model.cells.Position
import model.cells.properties.Direction
import model.room.RoomImpl
import utils.constants.GraphicManager.Point2D

import java.awt.Toolkit
import java.io.File
import java.nio.file.Paths

object GraphicManager:
  val AdjacentValues: Seq[Int] = Seq(0, 1, -1)
  val AdjacentDirection: Seq[(Int, Int)] =
    for
      x <- AdjacentValues
      y <- AdjacentValues
    yield (x, y)
  val Origin: Point2D = Point2D(0, 0)
  val ScrollBarBorderThickness: Int = 2
  val MenuGUIWidth: Int = 500
  val MenuGUIHeight: Int = 610
  val CellSize: Int = 32
  val Rows: Int = RoomImpl.DefaultHeight
  val Cols: Int = RoomImpl.DefaultWidth

  /** Point2D represents a coordinate
    * @param x
    *   the value of abscissa axis
    * @param y
    *   the value of ordinate axis
    */
  case class Point2D(x: Int, y: Int)
