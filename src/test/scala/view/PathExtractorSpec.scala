package view

import model.cells.{
  BasicCell,
  ButtonCell,
  Cell,
  CliffCell,
  Color,
  CoveredHoleCell,
  Direction,
  HoleCell,
  Item,
  PressableState,
  WallCell
}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import utils.ConstantUtils.AdjacentDirection
import utils.PathExtractor
import utils.PathExtractor.extractPath
import utils.TestUtils.*

class PathExtractorSpec extends AnyFlatSpec:

  "from a cell it" should "be able to extract a path for its respective image" in {
    val cellsWithPaths: List[(Cell, String)] = List(
      (BasicCell(position1_1), "cell_BS_W"),
      (WallCell(position2_1), "cell_WL"),
      (HoleCell(position1_2), "cell_HL_W_D"),
      (CliffCell(position2_2, Item.Empty, Direction.Left), "cell_CL_LEFT"),
      (CoveredHoleCell(position3_2), "cell_CH_W_C"),
      (ButtonCell(position3_1, color = Color.Red), "cell_BT_W_RED"),
      (ButtonCell(position3_3, color = Color.Blue, pressableState = PressableState.Pressed), "cell_BT_W_BLUE_P")
    )
    cellsWithPaths.foreach((c, s) => extractPath(c) should be(s))
  }

  "a path extractor" should "be able to compute the adjacent cells" in {
    PathExtractor.computeAdjacentCells(BasicCell(position2_1)) should be(
      for (x, y) <- AdjacentDirection
      yield (position2_1._1 + x, position2_1._2 + y)
    )
    PathExtractor.computeAdjacentCells(BasicCell(position3_3)) should be(
      for (x, y) <- AdjacentDirection
      yield (position3_3._1 + x, position3_3._2 + y)
    )
    PathExtractor.computeAdjacentCells(BasicCell(position1_1)) should not be (
      for (x, y) <- AdjacentDirection
      yield (position1_1._1 + x + x, position1_1._2 + y)
    )
  }

  "a path extractor" should "be able to" in {
    val cellsWithPaths: Set[Cell] = Set(
      WallCell(position1_1),
      WallCell(position1_2),
      ButtonCell(position1_3, color = Color.Red),
      WallCell(position2_1),
      CliffCell(position2_2, Item.Empty, Direction.Left),
      CoveredHoleCell(position2_3),
      ButtonCell(position3_1, color = Color.Blue, pressableState = PressableState.Pressed),
      CoveredHoleCell(position3_2),
      ButtonCell(position3_3, color = Color.Red)
    )
    println(extractPath(WallCell(position1_2), cellsWithPaths))
  }
