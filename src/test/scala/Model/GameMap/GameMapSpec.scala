package Model.GameMap

import Exceptions.RoomNotFoundException
import Model.Room.{Room, RoomBuilder}
import Model.Cells.Position
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.TryValues._
import Model.TestUtils.*

class GameMapSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var map: GameMap = _
  val mapName = "mappa 1"
  var rooms: Set[Room] = _
  var room: Room = _
  var roomName: String = _

  override def beforeEach(): Unit =
    super.beforeEach()
    room = RoomBuilder().build
    rooms = Set(room)
    roomName = room.name
    map = new GameMap(mapName, rooms, roomName, defaultPosition)

  "A map" should "have a name and contain  a room set, a starting room and a starting position" in {
    map.name should be(mapName)
    map.rooms shouldBe a[Set[Room]]
    map.initialRoom shouldBe a[String]
    map.initialPosition shouldBe a[Position]
  }

  "A map" should "retrieve a room having its name" in {
    map.getRoomFromName(roomName).isSuccess should be(true)
    map.getRoomFromName(roomName).success.value should be(room)
    // map.getRoomFromName("pippo") should
  }

  "A map" should "retrieve an error if the name is not present when trying to get a room" in {
    val wrongName: String = "pippo"
    map.getRoomFromName(wrongName).isFailure should be(true)
    map.getRoomFromName(wrongName).failure.exception shouldBe a[RoomNotFoundException]
  }
