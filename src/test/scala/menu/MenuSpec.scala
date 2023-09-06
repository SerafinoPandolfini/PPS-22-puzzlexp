package menu

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach

class MenuSpec extends AnyFlatSpec with BeforeAndAfterEach:
  super.beforeEach()

  "a controller" should "be able to continue the game" in {
    MenuController.continue should be(false)
    MenuController.startTheGame() should be(false)
    MenuController.continue = true
    MenuController.continue should be(true)
    MenuController.startTheGame() should be(true)
  }
