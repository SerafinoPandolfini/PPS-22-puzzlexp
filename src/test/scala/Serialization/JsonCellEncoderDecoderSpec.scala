package Serialization

import Model.Cells.{BasicCell, Cell, ButtonCell, Color}
import Model.TestUtils.*
import io.circe.parser.*
import io.circe.{Decoder, HCursor, Json}
import io.circe.syntax.*
import Serialization.JsonDecoder.cellDecoder
import Serialization.JsonEncoder.cellEncoder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*

class JsonCellEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var cell: Cell = _
  var cells: Set[Cell] = _

  override def beforeEach(): Unit =
    cell = BasicCell(defaultPosition)
    cells = Set(cell, ButtonCell(position1_1, color = Color.Blue))

  "A cell" should "be encodable and decodable in " in {
    cellEncoder.apply(cell) shouldBe a[Json]
    val cellJ: Json = cellEncoder.apply(cell)
    cellDecoder.apply(cellJ.hcursor).toOption.get should be(cell)
  }

  "A set of cell" should "be encodable and decodable in " in {
    cells.asJson shouldBe a[Json]
    val cellJ: Json = cells.asJson
    cellJ.hcursor.as[Set[Cell]].toOption.get should be(cells)
  }
