package model.room.rules

import model.cells.{
  BasicCell,
  ButtonBlockCell,
  ButtonCell,
  Color,
  Item,
  PressurePlateBlockCell,
  PressurePlateBlockGroup,
  PressurePlateCell,
  TeleportCell,
  TeleportDestinationCell,
  WallCell
}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.room.{Room, RoomBuilder, RoomLink}
import utils.TestUtils.*

class RoomRulesSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var perfectRoom: Room = _
  var flawedRoomBuilder: RoomBuilder = _
  var flawedRoom: Room = _
  val ruleViolations = 6

  override def beforeEach(): Unit =
    super.beforeEach()
    perfectRoom = RoomBuilder()
      .name("perfect room")
      .borderWalls()
      .addLinks(leftLink)
      .addCells(
        Set(
          PressurePlateCell(position1_1),
          PressurePlateBlockCell(position1_2, Item.Empty, PressurePlateBlockGroup.ObstacleWhenPressed),
          ButtonCell(position3_1, color = Color.Red),
          ButtonBlockCell(position2_2, color = Color.Red),
          TeleportCell(position2_1),
          TeleportDestinationCell(position3_3)
        )
      )
      .standardize
      .build
    flawedRoomBuilder = RoomBuilder()
      .name("flawed room")
      .addCell(WallCell(outOfBoundPosition))
      .addCell(BasicCell(defaultPosition))
      .addCell(PressurePlateCell(position1_1))
      .addCell(ButtonCell(position3_1, color = Color.Red))
      .addCell(TeleportCell(position2_1))
    flawedRoom = flawedRoomBuilder.build

  "A room" should "respect all room rules" in {
    roomRules.checkRoomValidity(flawedRoom).size should be(ruleViolations)
    roomRules.checkRoomValidity(perfectRoom) should be(List.empty[String])
  }

  "A room" should "have the option to show its eventual violations" in {
    flawedRoomBuilder.requestValidation.build
  }
