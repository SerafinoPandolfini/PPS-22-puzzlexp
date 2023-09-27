package serialization

import model.cells.*
import io.circe.parser.*
import io.circe.{Decoder, HCursor, Json}
import io.circe.syntax.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.{BeforeAndAfterEach, color}
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.properties.{Color, Direction, Item, PressurePlateBlockGroup}
import serialization.JsonCellDecoder.cellDecoder
import serialization.JsonCellEncoder.cellEncoder

class JsonCellEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var cell: Cell = _
  var cells: Set[Cell] = _

  override def beforeEach(): Unit =
    cell = BasicCell(DefaultPosition, Item.Pick)
    cells = Set(
      cell,
      ButtonCell(Position1_1, color = Color.Blue),
      ButtonBlockCell(Position1_2, color = Color.Blue),
      CliffCell(Position2_2, Item.Key, Direction.Down),
      HoleCell(Position2_1, Item.Box),
      PressurePlateCell(Position3_1),
      PressurePlateBlockCell(Position3_2, Item.Trunk, activeState = PressurePlateBlockGroup.ObstacleWhenPressed),
      RockCell(Position3_3),
      LockCell(DefaultPosition),
      PlantCell(DefaultPosition),
      CoveredHoleCell(DefaultPosition, Item.Coin),
      WallCell(DefaultPosition),
      TeleportCell(DefaultPosition),
      TeleportDestinationCell(DefaultPosition, Item.Axe)
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
