package Model.Room

import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec

class RoomBuilderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var baseRoom: Room = _
  val RoomName = "prova"

  override def beforeEach(): Unit =
    super.beforeEach()
    baseRoom = RoomBuilder().build

  "A room builder" should "always produce a complete set of cells" in {
    baseRoom.cells.size should be(Room.DefaultWidth * Room.DefaultHeight)
  }
