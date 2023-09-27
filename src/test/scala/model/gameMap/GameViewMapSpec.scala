package model.gameMap

import exceptions.{LinkNotFoundException, RoomNotFoundException}
import model.room.{Room, RoomBuilder, RoomLink}
import model.cells.Position
import model.cells.properties.Direction
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.TryValues.*
import utils.TestUtils.*

class GameViewMapSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:
  var map: GameMap = _
  var room: Room = _

  override def beforeEach(): Unit =
    super.beforeEach()
    room = RoomBuilder().name(RoomName).build
    map = new GameMap(MapName, Set(room), RoomName, DefaultPosition)

  "A map" should "have a name and contain  a room set, a starting room and a starting position" in {
    map.name should be(MapName)
    map.rooms shouldBe a[Set[Room]]
    map.initialRoom shouldBe a[String]
    map.initialPosition shouldBe a[Position]
  }

  "A map" should "retrieve a room having its name" in {
    map.getRoomFromName(RoomName).isSuccess should be(true)
    val room2 = map.getRoomFromName(RoomName).success.value
    isEqual(room2, room) should be(true)
  }

  "A map" should "retrieve an error if the name is not present when trying to get a room" in {
    val wrongName: String = FakeRoomName
    map.getRoomFromName(wrongName).isFailure should be(true)
    map.getRoomFromName(wrongName).failure.exception shouldBe a[RoomNotFoundException]
  }

  "A map" should "retrieve the info to get in the next room" in {
    val linkPosition: Position = Position1_1
    val newRoomName: String = Room2Name
    val newRoom: Room =
      Room(newRoomName, Set.empty, Set(RoomLink(linkPosition, GenericDirection, RoomName, DefaultPosition)))
    map = new GameMap(MapName, Set(room, newRoom), RoomName, DefaultPosition)
    map.changeRoom(linkPosition, newRoomName, GenericDirection).isSuccess should be(true)
    val (roomDest, posDest) = map.changeRoom(linkPosition, newRoomName, GenericDirection).success.value
    posDest should be(DefaultPosition)
    isEqual(room, roomDest) should be(true)
  }

  "A map" should "retrieve an error if the info to get in the next room are not present" in {
    Given("A room that doesn't exist")
    When("i try to go from the inexistent room somewhere")
    map.changeRoom(DefaultPosition, FakeRoomName, GenericDirection).isFailure should be(true)
    Then("i should get a RoomNotFoundException")
    map
      .changeRoom(DefaultPosition, FakeRoomName, GenericDirection)
      .failure
      .exception shouldBe a[RoomNotFoundException]
    Given("A room with no links")
    When("I try to change room")
    map.changeRoom(DefaultPosition, RoomName, GenericDirection).isFailure should be(true)
    Then("I should get a LinkNotFoundException")
    map
      .changeRoom(DefaultPosition, RoomName, GenericDirection)
      .failure
      .exception shouldBe a[LinkNotFoundException]
    Given("A room with a link to a non-existent room")
    room = Room(RoomName, Set.empty, Set(RoomLink(DefaultPosition, GenericDirection, FakeRoomName, Position1_1)))
    map = new GameMap(MapName, Set(room), RoomName, DefaultPosition)
    When("I try to use the link but with the wrong direction")
    map.changeRoom(DefaultPosition, RoomName, GenericDirection.opposite).isFailure should be(true)
    Then("I should get a LinkNotFoundException")
    map
      .changeRoom(DefaultPosition, RoomName, GenericDirection.opposite)
      .failure
      .exception shouldBe a[LinkNotFoundException]
    When("I try to use the link to the non-existent room with the correct direction")
    map.changeRoom(DefaultPosition, RoomName, GenericDirection).isFailure should be(true)
    Then("I should get a RoomNotFoundException")
    map
      .changeRoom(DefaultPosition, RoomName, GenericDirection)
      .failure
      .exception shouldBe a[RoomNotFoundException]
  }
