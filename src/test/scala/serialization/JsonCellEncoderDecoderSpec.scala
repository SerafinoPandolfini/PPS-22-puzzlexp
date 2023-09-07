package serialization

import model.cells.*
import utils.TestUtils.*
import io.circe.parser.*
import io.circe.{Decoder, HCursor, Json}
import io.circe.syntax.*
import serialization.JsonDecoder.cellDecoder
import serialization.JsonEncoder.cellEncoder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.{BeforeAndAfterEach, color}
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*

class JsonCellEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var cell: Cell = _
  var cells: Set[Cell] = _

  override def beforeEach(): Unit =
    cell = BasicCell(defaultPosition, Item.Pick)
    cells = Set(
      cell,
      ButtonCell(position1_1, color = Color.Blue),
      ButtonBlockCell(position1_2, color = Color.Blue),
      CliffCell(position2_2, Item.Key, Direction.Down),
      HoleCell(position2_1, Item.Box),
      PressurePlateCell(position3_1),
      PressurePlateBlockCell(position3_2, Item.Trunk, activeState = PressurePlateBlockGroup.ObstacleWhenPressed),
      RockCell(position3_3),
      LockCell(defaultPosition),
      PlantCell(defaultPosition),
      CoveredHoleCell(defaultPosition, Item.Coin),
      WallCell(defaultPosition),
      TeleportCell(defaultPosition),
      TeleportDestinationCell(defaultPosition, Item.Axe)
    )

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
