package EngineProlog

import Model.Cells.{BasicCell, Cell, WallCell}
import org.scalatest.{BeforeAndAfterEach, color}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import EngineProlog.PrologEngine.{*, given}
import alice.tuprolog.Struct
import EngineProlog.PrologConverter.convert
import Utils.TestUtils.*

class PrologEngineSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var engine: PrologEngine = _
  var cells: Set[Cell] = _

  override def beforeEach(): Unit =
    super.beforeEach()
    engine = PrologEngine("/PrologTheory/cellsCounter.pl")
    cells = Set(
      WallCell(defaultPosition),
      BasicCell(position1_1)
    )

  "A prolog engine" should "solve the provided goal and return the proper result" in {
    val term = "N"
    val input = Struct.of("cells_counter", cells.map(convert(_)).toList, term)
    val result = engine.solve(input, term)
    val size: Int = result(term)
    size should be(cells.size)
  }




