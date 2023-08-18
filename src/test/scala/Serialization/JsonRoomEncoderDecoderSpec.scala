package Serialization

import Model.Room.*
import Model.Cells.{BasicCell, Cell}
import Model.TestUtils.*
import io.circe.parser.*
import io.circe.{Decoder, HCursor, Json}
import io.circe.syntax.*
import Serialization.JsonDecoder.roomDecoder
import Serialization.JsonEncoder.roomEncoder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*

class JsonRoomEncoderDecoderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var room: Room = _

  override def beforeEach(): Unit =
    room = new Room(
      "name",
      Set(BasicCell(defaultPosition)),
      Set(RoomLink(defaultPosition, genericDirection, "room", position1_1))
    )

  "A room" should "be encodable and decodable in " in {
    roomEncoder.apply(room) shouldBe a[Json]
    val roomJ: Json = roomEncoder.apply(room)
    val room2 = roomDecoder.apply(roomJ.hcursor).toOption.get
    room2.name should be(room.name)
    room2.links should be(room.links)
    room2.cells should be(room.cells)
  }
