package model.room.rules

import model.cells.properties.{Color, Item, PressurePlateBlockGroup}
import model.cells.*
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
  val roomRules: RoomRules = RoomRules()

  override def beforeEach(): Unit =
    super.beforeEach()
    perfectRoom = RoomBuilder()
      .name("perfect room")
      .borderWalls()
      .addLinks(LeftLink)
      .addCells(
        Set(
          PressurePlateCell(Position1_1),
          PressurePlateBlockCell(Position1_2, Item.Empty, PressurePlateBlockGroup.ObstacleWhenPressed),
          ButtonCell(Position3_1, color = Color.Red),
          ButtonBlockCell(Position2_2, color = Color.Red),
          TeleportCell(Position2_1),
          TeleportDestinationCell(Position3_3)
        )
      )
      .standardize
      .build
    flawedRoomBuilder = RoomBuilder()
      .name("flawed room")
      .addCell(WallCell(OutOfBoundPosition))
      .addCell(BasicCell(DefaultPosition))
      .addCell(PressurePlateCell(Position1_1))
      .addCell(ButtonCell(Position3_1, color = Color.Red))
      .addCell(TeleportCell(Position2_1))
    flawedRoom = flawedRoomBuilder.build

  "A room" should "respect all room rules" in {
    roomRules.checkRoomValidity(flawedRoom).size should be(ruleViolations)
    roomRules.checkRoomValidity(perfectRoom) should be(List.empty[String])
  }

  "A room" should "have the option to show its eventual violations" in {
    flawedRoomBuilder.requestValidation.build
  }
