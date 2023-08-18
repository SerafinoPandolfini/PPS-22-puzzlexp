package Model.Room

import Model.Cells.{Cell, BasicCell, CliffCell, Item, Direction, Position}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import Model.TestUtils.*
import Exceptions.PlayerOutOfBoundsException

import scala.Option

class RoomSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var room: Room = _

  override def beforeEach(): Unit =
    super.beforeEach()
    room = RoomBuilder(RoomWidth, RoomHeight)
      .##()
      .++(
        Set(
          BasicCell(position2_2, Item.Box),
          BasicCell(position3_1),
          CliffCell(position2_1, Item.Empty, Direction.Left)
        )
      )
      .!!
      .build

  "A room" should "be able to update its cells items" in {
    room.getCell(position1_1).get.cellItem should be(Item.Empty)
    room.getCell(position2_2).get.cellItem should be(Item.Box)
    room.updateCellsItems(Set((position2_2, Item.Empty, genericDirection), (position1_1, Item.Box, genericDirection)))
    room.getCell(position1_1).get.cellItem should be(Item.Box)
    room.getCell(position2_2).get.cellItem should be(Item.Empty)
  }

  "A room" should "be able to check if the player is on a deadly cell" in {
    room.isPlayerDead(position1_1) should not be true
    room.isPlayerDead(outOfBoundPosition) match
      case Left(exception) => exception shouldBe a[PlayerOutOfBoundsException]
      case _               => fail("Expected Left(PlayerOutOfBoundsException) but got Right")
  }

  "A room" should "not let the player to walk into a non walkable cell" in {
    def calculateRepresentation(pos: Position): String =
      room.cellsRepresentation(Room.showPlayerAndBoxes(pos))

    // try to go into a wall
    var playerPosition: Position = position1_1
    var expectedRepresentation = "\n" +
      "WL | WL | WL | WL\n" +
      "WL | pl | CL |   \n" +
      "WL |    | bx | WL\n" +
      "WL | WL | WL | WL\n"
    playerPosition = room.playerMove(playerPosition, Direction.Up).get
    calculateRepresentation(playerPosition) should be(expectedRepresentation)
    // try to go in a cliff cell from the wrong direction
    playerPosition = room.playerMove(playerPosition, Direction.Right).get
    calculateRepresentation(playerPosition) should be(expectedRepresentation)
    // try to go out of room bounds
    playerPosition = position3_1
    expectedRepresentation = "\n" +
      "WL | WL | WL | WL\n" +
      "WL |    | CL | pl\n" +
      "WL |    | bx | WL\n" +
      "WL | WL | WL | WL\n"
    room.playerMove(playerPosition, Direction.Right) should be(Option.empty)
    calculateRepresentation(playerPosition) should be(expectedRepresentation)
  }
