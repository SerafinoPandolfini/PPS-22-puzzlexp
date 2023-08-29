package view

import model.cells.{BasicCell, Cell, CliffCell, HoleCell, WallCell, Item, Direction}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import utils.PathExtractor.extractPath
import utils.TestUtils.*

class PathExtractorSpec extends AnyFlatSpec:

  "from a cell it" should "be able to extract a path for its respective image" in {
    val cellsWithPaths: List[(Cell, String)] = List(
      (BasicCell(position1_1), "cell_BS_W"),
      (WallCell(position2_1), "cell_WL"),
      (HoleCell(position1_2), "cell_HL_W_D"),
      (CliffCell(position2_2, Item.Empty, Direction.Left), "cell_CL_LEFT")
    )
    cellsWithPaths.foreach((c, s) => extractPath(c) should be(s))
  }
