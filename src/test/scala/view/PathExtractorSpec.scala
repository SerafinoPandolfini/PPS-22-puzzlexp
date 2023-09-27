package view

import model.cells.properties.{Color, Direction, Item, PressableState}
import model.cells.{BasicCell, ButtonCell, Cell, CliffCell, CoveredHoleCell, HoleCell, WallCell}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import utils.PathExtractor.extractPath
import utils.TestUtils.*

class PathExtractorSpec extends AnyFlatSpec:

  "from a cell it" should "be able to extract a path for its respective image" in {
    val cellsWithPaths: List[(Cell, String)] = List(
      (BasicCell(Position1_1), "cell_BS_W"),
      (WallCell(Position2_1), "cell_WL"),
      (HoleCell(Position1_2), "cell_HL_W_D"),
      (CliffCell(Position2_2, Item.Empty, Direction.Left), "cell_CL_LEFT"),
      (CoveredHoleCell(Position3_2), "cell_CH_W_C"),
      (ButtonCell(Position3_1, color = Color.Red), "cell_BT_W_RED"),
      (ButtonCell(Position3_3, color = Color.Blue, pressableState = PressableState.Pressed), "cell_BT_W_BLUE_P")
    )
    cellsWithPaths.foreach((c, s) => extractPath(c) should be(s))
  }
