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
      .addCell(BasicCell(outOfBoundPosition))
      .standardize
      .build
    room.getCell(outOfBoundPosition) should be(Option.empty)
  }

  "A room builder" should "create the required room" in {
    Given("a room created with the builder")
    val room = RoomBuilder(RoomWidth, RoomHeight)
      .name(RoomName)
      .borderWalls()
      .addLinks(RoomLink(position3_2, Direction.Left, "", defaultPosition))
      .wallRectangle(position1_1, 1, 1)
      .addCells(Set(HoleCell(position2_2), CoveredHoleCell(position1_2)))
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

  "A room builder" should "have alias methods for creating a cell" in {
    val testCell = HoleCell(position2_2)
    Given("a room created using the builder standard methods")
    val room1 = RoomBuilder(RoomWidth, RoomHeight)
      .borderWalls()
      .wallRectangle(position1_1, 1, 1)
      .addCell(testCell)
      .standardize
      .build
    Given("another room created with the aliases")
    val room2 = RoomBuilder(RoomWidth, RoomHeight)
      .##()
      .||(position1_1, 1, 1)
      .+(testCell)
      .!!
      .build
    Then("they should be the same room")
    room1.cellsRepresentation() should be(room2.cellsRepresentation())
  }
