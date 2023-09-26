package model.room

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.BasicCell
import model.cells.properties.{Item, Direction}
import model.room.Room
import utils.extensions.RoomCellsRepresentation.{showPlayerAndBoxes, cellsRepresentation}

class BoxInteractionSpec extends AnyFlatSpec with GivenWhenThen:

  "A room" should "let the player move and interact with eventual items on walkable cells" in {
    Given("a room and a player position")
    val largerRoom = RoomBuilder(RoomWidth + 1, RoomHeight + 1)
      .##()
      .+(BasicCell(position3_1, Item.Box))
      .+(BasicCell(position2_2, Item.Box))
      .!!
      .build
    var playerPosition = position2_1
    var expectedRepresentation = "\n" +
      "WL | WL | WL | WL | WL\n" +
      "WL |    | pl | bx | WL\n" +
      "WL |    | bx |    | WL\n" +
      "WL |    |    |    | WL\n" +
      "WL | WL | WL | WL | WL\n"
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    When("the player tries to move a box into a wall")
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Right).get
    Then(noUpdateMessage)
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    playerPosition = position1_2
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(
      "\n" +
        "WL | WL | WL | WL | WL\n" +
        "WL |    |    | bx | WL\n" +
        "WL | pl | bx |    | WL\n" +
        "WL |    |    |    | WL\n" +
        "WL | WL | WL | WL | WL\n"
    )
    When("the player move a box into another cell")
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Right).get
    Then("the player should not move but the box is moved in the pressed direction")
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(
      "\n" +
        "WL | WL | WL | WL | WL\n" +
        "WL |    |    | bx | WL\n" +
        "WL | pl |    | bx | WL\n" +
        "WL |    |    |    | WL\n" +
        "WL | WL | WL | WL | WL\n"
    )
    playerPosition = position3_3
    expectedRepresentation = "\n" +
      "WL | WL | WL | WL | WL\n" +
      "WL |    |    | bx | WL\n" +
      "WL |    |    | bx | WL\n" +
      "WL |    |    | pl | WL\n" +
      "WL | WL | WL | WL | WL\n"
    When("the player tries to move a box into another box")
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
    Then(noUpdateMessage)
    playerPosition = largerRoom.playerMove(playerPosition, Direction.Up).get
    largerRoom.cellsRepresentation(showPlayerAndBoxes(playerPosition)) should be(expectedRepresentation)
  }
