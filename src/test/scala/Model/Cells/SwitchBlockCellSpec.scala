package Model.Cells

import Model.TestUtils.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import SwitchBlockGroup.*
import PressableState.*

class SwitchBlockCellSpec extends AnyFlatSpec with BeforeAndAfterEach:

  var switchBlockCell: SwitchBlockCell = _

  override def beforeEach(): Unit =
    super.beforeEach()
    switchBlockCell = SwitchBlockCell(defaultPosition, activeState = ObstacleWhenOff, NotPressed)

