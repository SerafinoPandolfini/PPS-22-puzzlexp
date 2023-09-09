package menu

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach

class MenuSpec extends AnyFlatSpec with BeforeAndAfterEach:
  super.beforeEach()

  "a controller menu" should "be able to get all the path of the map file" in {
    MenuController.start()
    MenuController.mapPathAndName.map((p, _) => p) should be(
      List(
        "src/main/resources/json/map01.json",
        "src/main/resources/json/mapProva.json",
        "src/main/resources/json/testMap.json"
      )
    )
  }

  "a controller menu" should "be able to get all the name of the map" in {
    MenuController.start()
    MenuController.mapPathAndName.map((_, n) => n) should be(List("map 1", "map1", "testMap"))
  }
