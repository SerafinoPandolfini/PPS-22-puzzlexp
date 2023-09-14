package utils

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.cells.{BasicCell, Cell}

class WallSpec extends AnyFlatSpec with BeforeAndAfterEach:

  "a path extractor" should "be able to compute the adjacent cells" in {
    PathExtractor.computeAdjacentCells(BasicCell((2, 0))) should be(
      List((2, 1), (3, 0), (2, -1), (1, 0), (3, 1), (1, 1), (1, -1), (3, -1))
    )
    PathExtractor.computeAdjacentCells(BasicCell((1, 2))) should be(
      List((1, 3), (2, 2), (1, 1), (0, 2), (2, 3), (0, 3), (0, 1), (2, 1))
    )
    PathExtractor.computeAdjacentCells(BasicCell((2, 0))) should not be (
      List((5, 6), (3, 0), (2, -1), (1, 0), (3, 1), (1, 1), (1, -1), (3, -1))
    )
  }
