package model.cells

import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.TestUtils.*
import model.cells.logic.CellExtension.{updateItem, moveIn}
import utils.extensions.PositionExtension.+
import model.cells.properties.{Item, Direction}

class TeleportCellSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:
  var teleportCell: TeleportCell = _
  var teleportDestinationCell: TeleportDestinationCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    teleportCell = TeleportCell(DefaultPosition)
    teleportDestinationCell = TeleportDestinationCell(Position2_2)

  "A teleport cell" should "update its item putting it on a teleportDestinationCell moved in the correct direction" in {
    Given("a set of TeleportCell, TeleportDestinationCell and BasicCells")
    val cells = (for
      x <- 0 to 3
      y <- 0 to 3
      position = (x, y)
    yield position match
      case p if p == DefaultPosition => teleportCell
      case p if p == Position2_2     => teleportDestinationCell
      case _                         => BasicCell(position)
    ).toSet
    When("a box is moved on the TeleportCell from the left")
    val updateCells = teleportCell.updateItem(cells, Item.Box, Direction.Left)
    Then("the box should be moved on the right of the TeleportDestinationCell")
    updateCells.head.cellItem should be(Item.Box)
    updateCells.head.position should be(Position2_2 + Direction.Left.coordinates)
  }

  "A teleport cell" should "bring the player to the teleport destination cell" in {
    val (_, dest) = teleportCell.moveIn(Set(teleportCell, teleportDestinationCell, BasicCell(Position1_1)))
    dest should be(teleportDestinationCell.position)
  }
