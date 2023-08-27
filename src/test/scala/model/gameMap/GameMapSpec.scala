package model.gameMap

import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.room.{Room, RoomBuilder, RoomLink}
import model.cells.{Direction, Position}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.TryValues.*
import utils.TestUtils.*

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
  }

  "A map" should "retrieve an error if the name is not present when trying to get a room" in {
    val wrongName: String = "pippo"
    map.getRoomFromName(wrongName).isFailure should be(true)
    map.getRoomFromName(wrongName).failure.exception shouldBe a[RoomNotFoundException]
  }

  "A map" should "retrieve the info to get in the next room" in {
    val pos2: Position = (1, 1)
    val name2: String = "room2"
    val room2: Room = Room(name2, Set.empty, Set(RoomLink(pos2, genericDirection, roomName, defaultPosition)))
    map = new GameMap(mapName, Set(room, room2), roomName, defaultPosition)
    map.changeRoom(pos2, name2, genericDirection).isSuccess should be(true)
    map.changeRoom(pos2, name2, genericDirection).success.value should be(room, defaultPosition)
  }

  "A map" should "retrieve an error if the info to get in the next room are not present" in {
    val wrongName: String = "pippo"
    // start room not present
    map.changeRoom(defaultPosition, wrongName, genericDirection).isFailure should be(true)
    map.changeRoom(defaultPosition, wrongName, genericDirection).failure.exception shouldBe a[RoomNotFoundException]
    // start room without the link
    map.changeRoom(defaultPosition, roomName, genericDirection).isFailure should be(true)
    map.changeRoom(defaultPosition, roomName, genericDirection).failure.exception shouldBe a[LinkNotFoundException]
    // start room with the link but it has the wrong direction
    room = Room(roomName, Set.empty, Set(RoomLink(defaultPosition, genericDirection, wrongName, (1, 1))))
    map = new GameMap(mapName, Set(room), roomName, defaultPosition)
    map.changeRoom(defaultPosition, roomName, genericDirection.opposite).isFailure should be(true)
    map
      .changeRoom(defaultPosition, roomName, genericDirection.opposite)
      .failure
      .exception shouldBe a[LinkNotFoundException]
    // room with the link but the destination room in the link doesn't exist
    map.changeRoom(defaultPosition, roomName, genericDirection).isFailure should be(true)
    map.changeRoom(defaultPosition, roomName, genericDirection).failure.exception shouldBe a[RoomNotFoundException]
  }
