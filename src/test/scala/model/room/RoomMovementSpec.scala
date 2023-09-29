package model.room

import org.scalatest.flatspec.AnyFlatSpec
import utils.TestUtils.*
import model.cells.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.TryValues.*
import exceptions.PlayerOutOfBoundsException
import model.cells.properties.{Color, PressableState, WalkableType}
import org.scalatest.GivenWhenThen

class RoomMovementSpec extends AnyFlatSpec with GivenWhenThen:

  "A room" should "be able to change according to player movement" in {
    Given("a room")
    val room2 = RoomBuilder(RoomWidth, RoomHeight)
      .borderWalls()
      .addCells(
        Set(
          TeleportDestinationCell(Position1_1),
          TeleportCell(Position2_2),
          ButtonCell(Position3_1, color = Color.Blue),
          ButtonBlockCell(Position2_1, color = Color.Blue, PressableState.NotPressed),
          CoveredHoleCell(Position3_3)
        )
      )
      .standardize
      .build

    When("the player moves on a teleport")
    room2.checkMovementConsequences(Position1_2, Position2_2).isSuccess should be(true)
    Then("The position should be the one of teleport destination")
    room2.checkMovementConsequences(Position1_2, Position2_2).success.value should be(Position1_1)

    When("The player moves out of the map")
    room2.checkMovementConsequences(Position5_1, Position4_1).isFailure should be(true)
    Then("a PlayerOutOfBoundsException should be thrown ")
    room2.checkMovementConsequences(Position5_1, Position4_1).failure.exception shouldBe a[PlayerOutOfBoundsException]
    room2.checkMovementConsequences(Position4_1, Position5_1).isFailure should be(true)
    room2.checkMovementConsequences(Position4_1, Position5_1).failure.exception shouldBe a[PlayerOutOfBoundsException]

    When("a player walk on a button")
    room2.getCell(Position2_1).get.walkableState should be(WalkableType.Walkable(false))
    room2.checkMovementConsequences(Position3_2, Position3_1)
    Then("the walkability of its block should change")
    room2.getCell(Position2_1).get.walkableState should be(WalkableType.Walkable(true))
    When("a player moves on a covered hole cell")
    room2.getCell(Position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(true)
    room2.checkMovementConsequences(Position3_2, Position3_3)
    Then("the cover should remain")
    room2.getCell(Position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(true)
    When("the player walks out of the covered hole cell")
    room2.checkMovementConsequences(Position3_3, Position3_2)
    Then("the cover should break")
    room2.getCell(Position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(false)
  }
