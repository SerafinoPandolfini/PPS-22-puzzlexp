package View

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

import javax.swing.JPanel

class GUITest extends AnyFlatSpec with BeforeAndAfterEach:
  var tile: Tile = _

  override def beforeEach(): Unit =
    super.beforeEach()
    val cellSize: Int = 32
    val panel: JPanel = JPanel()
    tile = Tile(panel, cellSize)

  "a tile" should "be able to place the character" in {
    tile.isCharacterHere should be(false)
    tile.placeCharacter("")
    tile.isCharacterHere should be(true)
  }
