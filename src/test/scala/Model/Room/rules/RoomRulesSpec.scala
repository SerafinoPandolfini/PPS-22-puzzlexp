package model.room.rules

import model.cells.WallCell
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.room.RoomBuilder
import model.room.Room
import utils.TestUtils.*

class RoomRulesSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var perfectRoom: Room = _
  var flawedRoom: Room = _
  val ruleViolations = 2

  override def beforeEach(): Unit =
    super.beforeEach()
    perfectRoom = RoomBuilder()
      .name("perfect room")
      .standardize
      .build
    flawedRoom = RoomBuilder()
      .name("flawed room")
      .addCell(WallCell(outOfBoundPosition))
      .build

  "A room" should "respect all room rules" in {
    roomRules.checkRoomValidity(flawedRoom).size should be(ruleViolations)
    roomRules.checkRoomValidity(perfectRoom) should be(List.empty[String])
  }
