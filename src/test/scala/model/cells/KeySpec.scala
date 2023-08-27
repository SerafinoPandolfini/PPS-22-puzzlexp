package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import model.TestUtils.defaultPosition

class KeySpec extends AnyFlatSpec with BeforeAndAfterEach:
  var keyCell: DoorCell = _

  /*override def beforeEach(): Unit =
    super.beforeEach()
    keyCell = KeyCell(defaultPosition)*/
