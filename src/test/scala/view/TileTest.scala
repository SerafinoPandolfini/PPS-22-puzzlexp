package view

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import utils.ImageManager
import javax.swing.JPanel

class TileTest extends AnyFlatSpec with BeforeAndAfterEach:
  var tile: Tile = _

  override def beforeEach(): Unit =
    super.beforeEach()
    val cellSize: Int = 32
    val panel: JPanel = JPanel()
    tile = Tile(panel, cellSize)

  "a tile" should "be able to place the character" in {
    tile.isCharacterHere should be(false)
    tile.placeCharacter(ImageManager.CharacterLeft.path)
    tile.isCharacterHere should be(true)
  }

  "a tile" should "be able to unplace the character" in {
    tile.placeCharacter(ImageManager.CharacterLeft.path)
    tile.isCharacterHere should be(true)
    tile.unplaceCharacter()
    tile.isCharacterHere should be(false)
  }
