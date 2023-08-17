package Model.GameMap

import Model.Room.{Room, RoomBuilder}
import Model.Cells.Position
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import Model.TestUtils.*

class GameMapSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var map: GameMap = _
  val mapName = "mappa 1"
  var rooms: Set[Room] = _

  override def beforeEach(): Unit =
    super.beforeEach()
    rooms = Set(RoomBuilder().build)
    map = new GameMap(mapName, rooms, rooms.head.name, defaultPosition)

  "A map" should "have a name and contain  a room set, a starting room and a starting position" in {
    map.name should be(mapName)
    map.rooms shouldBe a[Set[Room]]
    map.initialRoom shouldBe a[String]
    map.initialPosition shouldBe a[Position]
  }
