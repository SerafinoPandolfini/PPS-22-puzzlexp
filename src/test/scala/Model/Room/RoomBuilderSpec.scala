package Model.Room

import Model.Cells.BasicCell
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec

class RoomBuilderSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var baseRoom: Room = _
  val RoomName = "prova"
  val outOfBoundPosition = (-1, -1)

  override def beforeEach(): Unit =
    super.beforeEach()
    baseRoom = RoomBuilder().build

  "A room builder" should "always produce a complete set of cells" in {
    baseRoom.cells.size should be(Room.DefaultWidth * Room.DefaultHeight)
  }

  "A room builder" should "not have room outside its border" in {
    val room = RoomBuilder()
      .addCell(BasicCell(outOfBoundPosition))
      .standardize
      .build
    room.getCell(outOfBoundPosition) should be(Option.empty)
  }
