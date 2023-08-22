package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import Model.TestUtils.*
import Model.Cells.Extension.CellExtension.updateItem
import Model.Cells.Extension.PositionExtension.+

class TeleportCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var teleportCell: TeleportCell = _
  var teleportDestinationCell: TeleportDestinationCell = _
  val otherPosition: Position = (2, 2)

  override def beforeEach(): Unit =
    super.beforeEach()
    teleportCell = TeleportCell(defaultPosition)
    teleportDestinationCell = TeleportDestinationCell(otherPosition)

  "A teleport cell" should "update its item putting it on a teleportDestinationCell moved in the correct direction" in {
    val cells = (for
      x <- 0 to 3
      y <- 0 to 3
      position = (x, y)
    yield position match
      case p if p == defaultPosition => teleportCell
      case p if p == otherPosition   => teleportDestinationCell
      case _                         => BasicCell(position)
    ).toSet
    val updateCells = teleportCell.updateItem(cells, Item.Box, Direction.Left)
    updateCells.head.cellItem should be(Item.Box)
    updateCells.head.position should be(otherPosition + Direction.Left.coordinates)
  }
