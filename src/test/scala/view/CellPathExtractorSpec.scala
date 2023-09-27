package view

import model.cells.properties.{Color, Direction, Item, PressableState}
import model.cells.{BasicCell, ButtonCell, Cell, CliffCell, CoveredHoleCell, HoleCell, WallCell}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import utils.constants.GraphicManager
import utils.extensions.paths.CellPathExtractor.extractCellPath
import utils.TestUtils.*
import utils.constants.GraphicManager.AdjacentDirection
import utils.extensions.paths.CellPathExtractor.extractCellPath

class CellPathExtractorSpec extends AnyFlatSpec:

  "from a cell it" should "be able to extract a path for its respective image" in {
    val cellsWithPaths: List[(Cell, String)] = List(
      (BasicCell(position1_1), "cell_BS_W"),
      (WallCell(position2_1), "cell_WL_C"),
      (HoleCell(position1_2), "cell_HL_W_D"),
      (CliffCell(position2_2, Item.Empty, Direction.Left), "cell_CL_LEFT"),
      (CoveredHoleCell(position3_2), "cell_CH_W_C"),
      (ButtonCell(position3_1, color = Color.Red), "cell_BT_W_RED"),
      (ButtonCell(position3_3, color = Color.Blue, pressableState = PressableState.Pressed), "cell_BT_W_BLUE_P")
    )
    cellsWithPaths.foreach((c, s) => c.extractCellPath() should be(s))
  }

  "a path extractor" should "be able to compute the string path of a specific cell" in {
    val cellsWithPaths: Set[Cell] = Set(
      WallCell(position1_1),
      WallCell(position1_2),
      ButtonCell(position1_3, color = Color.Red),
      WallCell(position2_1),
      CoveredHoleCell(position2_3),
      ButtonCell(position3_1, color = Color.Blue, pressableState = PressableState.Pressed),
      CoveredHoleCell(position3_2),
      ButtonCell(position3_3, color = Color.Red)
    )
    WallCell(position2_2).extractCellPath(cellsWithPaths) should be("cell_WL_SE")
  }
