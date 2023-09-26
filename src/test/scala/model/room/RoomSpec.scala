package model.room

import model.cells.*
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import utils.TestUtils.*
import exceptions.PlayerOutOfBoundsException
import scala.Option
import scala.util.Failure
import utils.extensions.RoomCellsRepresentation.{cellsRepresentation, showPlayerAndBoxes}
import model.cells.properties.{Item, Direction}

class RoomSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:
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
    Given("a room and a player position")
    var playerPosition: Position = position1_1
    var expectedRepresentation = "\n" +
      "WL | WL | WL | WL\n" +
      "WL | pl | CL |   \n" +
      "WL |    | bx | WL\n" +
      "WL | WL | WL | WL\n"
    When("the player try to moves into a wall")
    playerPosition = room.playerMove(playerPosition, Direction.Up).get
    Then(noUpdateMessage)
    room.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    When("the player try to moves into a cliff cell from the wrong direction")
    playerPosition = room.playerMove(playerPosition, Direction.Right).get
    Then(noUpdateMessage)
    room.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    playerPosition = position3_1
    When("the player tries to move out of the room")
    Then("its position should be an Option Empty")
    room.playerMove(playerPosition, Direction.Right) should be(Option.empty)
  }
