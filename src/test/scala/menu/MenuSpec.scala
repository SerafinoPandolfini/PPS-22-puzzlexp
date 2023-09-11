package menu

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach

class MenuSpec extends AnyFlatSpec with BeforeAndAfterEach:
  super.beforeEach()

  "a controller menu" should "be able to get all the path of the map file" in {
    MenuController.start()
    MenuController.mapPathAndName.map((p, _) => p) shouldBe a[List[String]]
  }

  "a controller menu" should "be able to get all the name of the map" in {
    MenuController.start()
    MenuController.mapPathAndName.map((_, n) => n) shouldBe a[List[String]]
  }
