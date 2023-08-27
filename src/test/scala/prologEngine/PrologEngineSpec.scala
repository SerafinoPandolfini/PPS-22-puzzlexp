package prologEngine

import model.cells.{BasicCell, Cell, WallCell}
import org.scalatest.{BeforeAndAfterEach, color}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import prologEngine.PrologEngine.{*, given}
import alice.tuprolog.Struct
import prologEngine.PrologConverter.*
import utils.TestUtils.*

class PrologEngineSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var engine: PrologEngine = _
  var cells: Set[Cell] = _

  override def beforeEach(): Unit =
    super.beforeEach()
    engine = PrologEngine("/prologTheory/cells_counter.pl")
    cells = Set(
      WallCell(defaultPosition),
      BasicCell(position1_1)
    )

  "A prolog engine" should "solve the provided goal and return the proper result" in {
    val term = "N"
    val input = Struct.of("cells_counter", cells.map(convertCellToProlog(_)).toList, term)
    val result = engine.solve(input, term)
    val size: Int = result(term)
    size should be(cells.size)
  }
