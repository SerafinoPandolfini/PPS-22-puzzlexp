package model.room

import model.cells.{Cell, BasicCell, CliffCell, Item, Direction, Position}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import model.TestUtils.*
import exceptions.PlayerOutOfBoundsException

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
    // try to go into a wall
    var playerPosition: Position = position1_1
    var expectedRepresentation = "\n" +
      "WL | WL | WL | WL\n" +
      "WL | pl | CL |   \n" +
      "WL |    | bx | WL\n" +
      "WL | WL | WL | WL\n"
    playerPosition = room.playerMove(playerPosition, Direction.Up).get
    room.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    // try to go in a cliff cell from the wrong direction
    playerPosition = room.playerMove(playerPosition, Direction.Right).get
    room.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    // try to go out of room bounds
    playerPosition = position3_1
    room.playerMove(playerPosition, Direction.Right) should be(Option.empty)
  }

  "A room" should "let the player move and interact with eventual items on walkable cells" in {
    val largerRoom = RoomBuilder(RoomWidth + 1, RoomHeight + 1)
      .##()
      .+(BasicCell(position3_1, Item.Box))
      .+(BasicCell(position2_2, Item.Box))
      .!!
      .build
    // try to move a box into a wall
    var playerPosition = position2_1
    var expectedRepresentation = "\n" +
      "WL | WL | WL | WL | WL\n" +
      "WL |    | pl | bx | WL\n" +
      "WL |    | bx |    | WL\n" +
      "WL |    |    |    | WL\n" +
      "WL | WL | WL | WL | WL\n"
    largerRoom.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Right).get
    // the representation does not change
    largerRoom.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    // move a box
    playerPosition = position1_2
    largerRoom.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(
      "\n" +
        "WL | WL | WL | WL | WL\n" +
        "WL |    |    | bx | WL\n" +
        "WL | pl | bx |    | WL\n" +
        "WL |    |    |    | WL\n" +
        "WL | WL | WL | WL | WL\n"
    )
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Right).get
    largerRoom.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(
      "\n" +
        "WL | WL | WL | WL | WL\n" +
        "WL |    |    | bx | WL\n" +
        "WL | pl |    | bx | WL\n" +
        "WL |    |    |    | WL\n" +
        "WL | WL | WL | WL | WL\n"
    )
    // try to move a box into a box
    playerPosition = position3_3
    expectedRepresentation = "\n" +
      "WL | WL | WL | WL | WL\n" +
      "WL |    |    | bx | WL\n" +
      "WL |    |    | bx | WL\n" +
      "WL |    |    | pl | WL\n" +
      "WL | WL | WL | WL | WL\n"
    largerRoom.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Up).get
    // the representation does not change
    largerRoom.cellsRepresentation(Room.showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
  }
