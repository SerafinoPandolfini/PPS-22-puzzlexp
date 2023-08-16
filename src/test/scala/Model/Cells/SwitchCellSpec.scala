package Model.Cells

import Model.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class SwitchCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var switchCell: SwitchCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    switchCell = SwitchCell(defaultPosition)

