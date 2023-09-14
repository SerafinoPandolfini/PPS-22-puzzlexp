package serialization

import model.cells.{BasicCell, Cell}
import model.gameMap.*
import model.room.*
import utils.TestUtils.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.{Decoder, HCursor, Json}
import serialization.JsonDecoder.{mapDecoder, getJsonFromPath}
import serialization.JsonEncoder.mapEncoder
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*

class JsonMapEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var map: GameMap = _

  override def beforeEach(): Unit =
    val room = Room(
      "name",
      Set(BasicCell(defaultPosition)),
      Set(RoomLink(defaultPosition, genericDirection, "room", position1_1))
    )
    map = new GameMap("map1", Set(room), "name", defaultPosition)

  "A map" should "be encodable and decodable in " in {
    mapEncoder.apply(map) shouldBe a[Json]
    val mapJ: Json = mapEncoder.apply(map)
    println(mapDecoder.apply(mapJ.hcursor))
    val map2 = mapDecoder.apply(mapJ.hcursor).toOption.get
    isEqual(map, map2) should be(true)
  }

  "A map" should "be retrievable from a json file" in {
    val j = getJsonFromPath("src/main/resources/json/testMap.json").toOption.get
    j shouldBe a[Json]
    mapDecoder.apply(j.hcursor).toOption.get shouldBe a[GameMap]
  }
