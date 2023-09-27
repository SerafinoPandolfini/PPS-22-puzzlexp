package model.gameMap

import model.cells.properties.Direction
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.flatspec.AnyFlatSpec
import utils.TestUtils.{DefaultPosition, Position1_1}

class MinimapElementSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:

  var visitableElement: MinimapElement = _
  var placeholderElement: MinimapElement = _

  override def beforeEach(): Unit =
    visitableElement = MinimapElement("visitable", DefaultPosition, Set.empty[Direction], false, true)
    placeholderElement = MinimapElement("placeholder", Position1_1, Set.empty[Direction], false, false)

  "A MinimapElement" should "be visitable if it exist" in {
    Given("a visitable element")
    When("visited by the player")
    Then("it should be visited")
    visitableElement.visit().visited should be(true)
    Given("a placeholder element")
    When("visited by the player")
    Then("it should not be visited")
    placeholderElement.visit().visited should not be true
  }
