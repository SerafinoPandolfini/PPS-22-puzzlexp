package Model.Room

import Model.Cells.{BasicCell, HoleCell, CoveredHoleCell, Direction}
import Model.TestUtils.*
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

  "A room builder" should "not have room outside its border" in {
    val room = RoomBuilder()
      .addCell(BasicCell(outOfBoundPosition))
      .standardize
      .build
    room.getCell(outOfBoundPosition) should be(Option.empty)
  }

  "A room builder" should "create the required room" in {
    val room = RoomBuilder()
      .name(RoomName)
      .borderWalls() // the border of the cell is WL
      .addLinks(RoomLink((position3_2), Direction.Left, "", defaultPosition)) // cell in (3, 2) becomes BasicCell
      .wallRectangle(position1_1, 1, 1) // (1, 1) becomes wall
      .addCells(Set(HoleCell((position2_2)), CoveredHoleCell((position1_2)))) // (2, 2) becomes HL, (1, 2) becomes CH
      .standardize
      .build

    room.name should be(RoomName)
    room.cellsRepresentation should be(
      "\n" +
        "WL | WL | WL | WL\n" +
        "WL | WL |    | WL\n" +
        "WL | CH | HL |   \n" +
        "WL | WL | WL | WL\n"
    )
  }
