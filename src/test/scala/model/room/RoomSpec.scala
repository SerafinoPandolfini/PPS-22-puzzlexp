package model.room

import model.cells.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.TryValues.*
import utils.TestUtils.*
import exceptions.PlayerOutOfBoundsException
import scala.Option
import scala.util.Failure
import utils.RoomCellsRepresentation.{cellsRepresentation, showPlayerAndBoxes}

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
      case Failure(exception) => exception shouldBe a[PlayerOutOfBoundsException]
      case _                  => fail("Expected Left(PlayerOutOfBoundsException) but got Right")
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
    room.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    // try to go in a cliff cell from the wrong direction
    playerPosition = room.playerMove(playerPosition, Direction.Right).get
    room.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
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
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Right).get
    // the representation does not change
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    // move a box
    playerPosition = position1_2
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(
      "\n" +
        "WL | WL | WL | WL | WL\n" +
        "WL |    |    | bx | WL\n" +
        "WL | pl | bx |    | WL\n" +
        "WL |    |    |    | WL\n" +
        "WL | WL | WL | WL | WL\n"
    )
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Right).get
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(
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
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Up).get
    // the representation does not change
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
  }

  "A room" should "be able to change according to player movement" in {
    val room2 = RoomBuilder(RoomWidth, RoomHeight)
      .##()
      .++(
        Set(
          TeleportDestinationCell(position1_1),
          TeleportCell(position2_2),
          ButtonCell(position3_1, color = Color.Blue),
          ButtonBlockCell(position2_1, color = Color.Blue, PressableState.NotPressed),
          CoveredHoleCell(position3_3)
        )
      )
      .!!
      .build

    // check correct cell for player
    room2.checkMovementConsequences(position1_2, position2_2).isSuccess should be(true)
    room2.checkMovementConsequences(position1_2, position2_2).success.value should be(position1_1)

    // check errors
    room2.checkMovementConsequences((5, 1), (4, 1)).isFailure should be(true)
    room2.checkMovementConsequences((5, 1), (4, 1)).failure.exception shouldBe a[PlayerOutOfBoundsException]
    room2.checkMovementConsequences((4, 1), (5, 1)).isFailure should be(true)
    room2.checkMovementConsequences((4, 1), (5, 1)).failure.exception shouldBe a[PlayerOutOfBoundsException]

    // check cell set update
    room2.getCell(position2_1).get.walkableState should be(WalkableType.Walkable(false))
    room2.checkMovementConsequences(position3_2, position3_1)
    room2.getCell(position2_1).get.walkableState should be(WalkableType.Walkable(true))
    room2.getCell(position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(true)
    room2.checkMovementConsequences(position3_2, position3_3)
    room2.getCell(position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(true)
    room2.checkMovementConsequences(position3_3, position3_2)
    room2.getCell(position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(false)
  }
