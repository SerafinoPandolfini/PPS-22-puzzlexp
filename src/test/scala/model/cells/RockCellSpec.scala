package model.cells

import model.TestUtils.{defaultPosition, genericDirection}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.cells.extension.CellExtension.updateItem
import model.cells.extension.PowerUpExtension.*

class RockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var rockCell: RockCell = _
  var brokenRockCell: RockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    rockCell = RockCell(defaultPosition)
    brokenRockCell = rockCell.copy(broken = true)

  "A rock cell" should "be not broken" in {
    rockCell.broken should be(false)
  }

  "only a broken rock" should "be walkable" in {
    rockCell.broken should be(false)
    rockCell.walkableState should be(WalkableType.Walkable(false))
    brokenRockCell.broken should be(true)
    brokenRockCell.walkableState should be(WalkableType.Walkable(true))
  }

  "a rock cell if it is not broken" should "not contain a box" in {
    var cells: Set[Cell] = Set(rockCell)
    rockCell.broken should be(false)
    cells = rockCell.updateItem(cells, Item.Box, genericDirection)
    rockCell = cells.head match
      case cell: RockCell => cell
    rockCell.cellItem should be(Item.Empty)
  }

  "a rock cell if it's broken" should "contain a box" in {
    var cells: Set[Cell] = Set(brokenRockCell)
    brokenRockCell.broken should be(true)
    cells = brokenRockCell.updateItem(cells, Item.Box, genericDirection)
    brokenRockCell = cells.collectFirst { case cell: RockCell => cell }.get
    brokenRockCell.cellItem should be(Item.Box)
  }

  "only a pick" should "be able to break a rock" in {
    var cells: Set[Cell] = Set(rockCell)
    cells = rockCell.usePowerUp(Item.Axe)
    rockCell = cells.head match
      case cell: RockCell => cell
    rockCell.broken should be(false)
    cells = rockCell.usePowerUp(Item.Pick)
    rockCell = cells.head match
      case cell: RockCell => cell
    rockCell.broken should be(true)
  }
