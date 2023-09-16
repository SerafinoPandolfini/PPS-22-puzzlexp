package utils

import model.room.RoomImpl
import model.cells.{Direction, Position}
import utils.ConstantUtils.Point2D

import java.io.File
import java.nio.file.Paths
import java.awt.Toolkit

object ConstantUtils:
  // non usate ??
  // val GenericDirection: Direction = Direction.Down
  // val DefaultPosition: Position = (0, 0)
  // val FillerPlayButtonHeight: Int = ControlsPanelSize - 190
  val Origin: Point2D = Point2D(0, 0)
  val ScrollBarBorderThickness: Int = 2
  val MenuGUIWidth: Int = 500
  val MenuGUIHeight: Int = 610
  val JsonDirectoryPath: String = "src/main/resources/json/"
  val CellSize: Int = 32
  val ScreenHeight: Int = Toolkit.getDefaultToolkit.getScreenSize.height
  val Rows: Int = RoomImpl.DefaultHeight
  val Cols: Int = RoomImpl.DefaultWidth

  /** Point2D represents a coordinate
    * @param x
    *   the value of abscissa axis
    * @param y
    *   the value of ordinate axis
    */
  case class Point2D(x: Int, y: Int)
