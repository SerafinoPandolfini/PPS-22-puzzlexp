package model.room

import model.cells.properties.Direction
import model.cells.{BasicCell, HoleCell, CoveredHoleCell}
import utils.TestUtils.*
import model.room.RoomImpl
import model.room.RoomBuilder.*
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import utils.TestUtils.*
import utils.extensions.RoomCellsRepresentation.cellsRepresentation

class RoomBuilderSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:
  var baseRoom: Room = _
  val RoomName = "testRoom"

  override def beforeEach(): Unit =
    super.beforeEach()
    baseRoom = RoomBuilder().build

  "A room builder" should "always produce a complete set of cells" in {
    baseRoom.cells.size should be(RoomImpl.DefaultWidth * RoomImpl.DefaultHeight)
  }

  "A room builder" should "not have room outside its border" in {
    val room = RoomBuilder()
      .addCell(BasicCell(OutOfBoundPosition))
      .standardize
      .build
    room.getCell(OutOfBoundPosition) should be(Option.empty)
  }

  "A room builder" should "create the required room" in {
    Given("a room created with the builder")
    val room = RoomBuilder(RoomWidth, RoomHeight)
      .name(RoomName)
      .borderWalls()
      .addLinks(RoomLink(Position3_2, Direction.Left, "", DefaultPosition))
      .wallRectangle(Position1_1, 1, 1)
      .addCells(Set(HoleCell(Position2_2), CoveredHoleCell(Position1_2)))
      .standardize
      .build
    Then("it should match with the one described in the builder")
    room.name should be(RoomName)
    room.cellsRepresentation() should be(
      "\n" +
        "WL | WL | WL | WL\n" +
        "WL | WL |    | WL\n" +
        "WL | CH | HL |   \n" +
        "WL | WL | WL | WL\n"
    )
  }
