package model.room

import org.scalatest.flatspec.AnyFlatSpec
import utils.TestUtils.*
import model.cells.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.TryValues.*
import exceptions.PlayerOutOfBoundsException
import model.cells.properties.{Color, PressableState, WalkableType}

class RoomMovementSpec extends AnyFlatSpec:

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

    room2.checkMovementConsequences(position1_2, position2_2).isSuccess should be(true)
    room2.checkMovementConsequences(position1_2, position2_2).success.value should be(position1_1)

    room2.checkMovementConsequences((5, 1), (4, 1)).isFailure should be(true)
    room2.checkMovementConsequences((5, 1), (4, 1)).failure.exception shouldBe a[PlayerOutOfBoundsException]
    room2.checkMovementConsequences((4, 1), (5, 1)).isFailure should be(true)
    room2.checkMovementConsequences((4, 1), (5, 1)).failure.exception shouldBe a[PlayerOutOfBoundsException]

    room2.getCell(position2_1).get.walkableState should be(WalkableType.Walkable(false))
    room2.checkMovementConsequences(position3_2, position3_1)
    room2.getCell(position2_1).get.walkableState should be(WalkableType.Walkable(true))
    room2.getCell(position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(true)
    room2.checkMovementConsequences(position3_2, position3_3)
    room2.getCell(position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(true)
    room2.checkMovementConsequences(position3_3, position3_2)
    room2.getCell(position3_3).get.asInstanceOf[CoveredHoleCell].cover should be(false)
  }
