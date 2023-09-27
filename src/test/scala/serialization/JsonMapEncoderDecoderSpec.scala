package serialization

import model.cells.{BasicCell, Cell}
import model.gameMap.*
import model.room.*
import serialization.JsonDecoder.{mapDecoder, getJsonFromPath}
import serialization.JsonEncoder.mapEncoder
import utils.TestUtils.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.{Decoder, HCursor, Json}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class JsonMapEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var map: GameMap = _

  override def beforeEach(): Unit =
    val room = Room(
      RoomName,
      Set(BasicCell(DefaultPosition)),
      Set(RoomLink(DefaultPosition, GenericDirection, Room2Name, Position1_1))
    )
    map = new GameMap(MapName, Set(room), RoomName, DefaultPosition)

  "A map" should "be encodable and decodable in " in {
    mapEncoder.apply(map) shouldBe a[Json]
    val mapJ: Json = mapEncoder.apply(map)
    val map2 = mapDecoder.apply(mapJ.hcursor).toOption.get
    isEqual(map, map2) should be(true)
  }

  "A map" should "be retrievable from a json file" in {
    val j = getJsonFromPath(JsonTestFile).toOption.get
    j shouldBe a[Json]
    mapDecoder.apply(j.hcursor).toOption.get shouldBe a[GameMap]
  }
