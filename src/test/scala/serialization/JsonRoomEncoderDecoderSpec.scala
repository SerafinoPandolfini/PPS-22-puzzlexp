package serialization

import model.room.*
import model.cells.{BasicCell, Cell}
import utils.TestUtils.*
import io.circe.parser.*
import io.circe.{Decoder, HCursor, Json}
import io.circe.syntax.*
import serialization.JsonDecoder.roomDecoder
import serialization.JsonEncoder.roomEncoder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*

class JsonRoomEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var room: Room = _

  override def beforeEach(): Unit =
    room = Room(
      "name",
      Set(BasicCell(defaultPosition)),
      Set(RoomLink(defaultPosition, genericDirection, "room", position1_1))
    )

  "A room" should "be encodable and decodable in " in {
    roomEncoder.apply(room) shouldBe a[Json]
    val roomJ: Json = roomEncoder.apply(room)
    val room2 = roomDecoder.apply(roomJ.hcursor).toOption.get
    isEqual(room, room2) should be(true)
  }
