package utils

import model.cells.{Direction, Position}

object ConstantUtils:
  val CoinValue: Int = 10
  val BagValue: Int = 20
  val TrunkValue: Int = 50
  val NotValuable: Int = 0
  val GenericDirection: Direction = Direction.Down
  val DefaultPosition: Position = (0, 0)
  val ControlsText: String = "CONTROLS"
  val PlayText: String = "PLAY"
  val MenuGUIWidth: Int = 500
  val MenuGUIHeight: Int = 610
  val BackgroundPanelHeight: Int = 580
  val ButtonsPanelHeight: Int = 50
  val ButtonsPanelWidth: Int = 400
  val Origin: Point2D = Point2D(0, 0)
  val ButtonCoordinate: Point2D = Point2D(40, 200)
  val ButtonWidth: Int = 150
  val ButtonHeight: Int = 40
  val BorderRadius: Int = 20

  /** Point2D represents a coordinate
    * @param x
    *   the value of abscissa axis
    * @param y
    *   the value of ordinate axis
    */
  case class Point2D(x: Int, y: Int)
