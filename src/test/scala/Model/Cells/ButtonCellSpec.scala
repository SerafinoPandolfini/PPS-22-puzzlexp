package Model.Cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import Model.TestUtils.*
import Color.*

class ButtonCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var buttonCell: ButtonCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    buttonCell = ButtonCell(defaultPosition, color = Blue)

