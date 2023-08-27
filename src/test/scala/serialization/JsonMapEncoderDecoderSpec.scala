package serialization

import model.cells.{BasicCell, Cell}
import model.gameMap.*
import model.room.*
import model.TestUtils.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.{Decoder, HCursor, Json}
import serialization.JsonDecoder.{mapDecoder, getJsonFromPath, getAbsolutePath}
import serialization.JsonEncoder.mapEncoder
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class JsonMapEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var map: GameMap = _

  override def beforeEach(): Unit =
    val room = new Room(
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
    map2.name should be(map.name)
    map2.rooms.size should be(map.rooms.size)
    val numbereq = for
      m2 <- map2.rooms
      m <- map.rooms
      if m.name == m2.name
      if m.links == m2.links
      if m.cells == m2.cells
    yield m2
    numbereq.size should be(map.rooms.size)
    map2.initialRoom should be(map.initialRoom)
    map2.initialPosition should be(map.initialPosition)
  }

  "A map" should "be retrievable from a json file" in {
    val pathabs = getAbsolutePath("src/main/scala/Json/map01.json")
    val j = getJsonFromPath(pathabs).toOption.get
    j shouldBe a[Json]
    mapDecoder.apply(j.hcursor).toOption.get shouldBe a[GameMap]
  }
