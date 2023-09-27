package model.cells

import utils.TestUtils.{DefaultPosition, GenericDirection}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.cells.logic.CellExtension.updateItem
import model.cells.logic.UseItemExtension.*
import model.cells.properties.{Item, WalkableType}
import model.game.CurrentGame

class RockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var rockCell: RockCell = _
  var brokenRockCell: RockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    rockCell = RockCell(DefaultPosition)
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
    cells = rockCell.updateItem(cells, Item.Box, GenericDirection)
    rockCell = cells.head match
      case cell: RockCell => cell
    rockCell.cellItem should be(Item.Empty)
  }

  "a rock cell if it's broken" should "contain a box" in {
    var cells: Set[Cell] = Set(brokenRockCell)
    brokenRockCell.broken should be(true)
    cells = brokenRockCell.updateItem(cells, Item.Box, GenericDirection)
    brokenRockCell = cells.collectFirst { case cell: RockCell => cell }.get
    brokenRockCell.cellItem should be(Item.Box)
  }

  "only a pick" should "be able to break a rock" in {
    var cells: Set[Cell] = Set(rockCell)
    CurrentGame.addItem(Item.Axe)
    cells = cells.concat(rockCell.usePowerUp())
    rockCell = cells.head match
      case cell: RockCell => cell
    rockCell.broken should be(false)
    CurrentGame.addItem(Item.Pick)
    cells = rockCell.usePowerUp()
    rockCell = cells.head match
      case cell: RockCell => cell
    rockCell.broken should be(true)
  }
