package utils

import model.cells.{Direction, Position}

import java.io.File
import java.nio.file.Paths

object ConstantUtils:
  val CoinValue: Int = 10
  val BagValue: Int = 20
  val TrunkValue: Int = 50
  val NotValuable: Int = 0
  val GenericDirection: Direction = Direction.Down
  val DefaultPosition: Position = (0, 0)
  val ControlsText: String = "CONTROLS"
  val PlayText: String = "PLAY"
  val ContinueText: String = "CONTINUE"
  val NewGameText: String = "NEW GAME"
  val MenuGUIWidth: Int = 500
  val MenuGUIHeight: Int = 610
  val ControlsGUISize: Int = 510
  val ControlsPanelSize: Int = ControlsGUISize - 10
  val BackgroundPanelHeight: Int = 580
  val ButtonsPanelHeight: Int = 100
  val ButtonsPanelWidth: Int = 400
  val Origin: Point2D = Point2D(0, 0)
  val ButtonCoordinate: Point2D = Point2D(40, 200)
  val ButtonWidth: Int = 150
  val ButtonHeight: Int = 40
  val BorderRadius: Int = 20
  val SelectLabelFontSize: Int = 30
  val SelectLabelSize: Int = 400
  val SelectCellHeight: Int = 20
  val ScrollBarBorderThickness: Int = 2
  val ScrollBarWidth: Int = 10
  val ZeroDimension: Int = 0
  val JsonDirectoryPath: String = "src/main/resources/json/"
  val SavesDirectoryPath: String = Paths
    .get(System.getProperty("user.home"), "puzzlexp", "saves")
    .toString + File.separator
  val CheckExistingFile: String = "src/main/resources/saves/FirstMap.json"
  val CheckWrongFile: String = "src/main/resources/saves/ZeroMap.json"
  val JsonExtension: String = ".json"
  val FillerWidth: Int = 0
  val FillerHeight: Int = 40
  val FillerPlayButtonHeight: Int = ControlsPanelSize - 190

  /** Point2D represents a coordinate
    * @param x
    *   the value of abscissa axis
    * @param y
    *   the value of ordinate axis
    */
  case class Point2D(x: Int, y: Int)
