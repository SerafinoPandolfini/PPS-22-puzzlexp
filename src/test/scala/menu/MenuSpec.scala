package menu

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.BeforeAndAfterEach

import java.awt.GraphicsEnvironment

class MenuSpec extends AnyFlatSpec with BeforeAndAfterEach:

  override def beforeEach(): Unit =
    if !GraphicsEnvironment.isHeadless then MenuController.start()
    super.beforeEach()

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then MenuController.view.dispose()

  "a controller menu" should "be able to get all the path of the map file" in {
    if !GraphicsEnvironment.isHeadless then
      MenuController.start()
      MenuController.mapPathAndName.map((p, _) => p) shouldBe a[List[String]]
  }

  "a controller menu" should "be able to get all the name of the map" in {
    if !GraphicsEnvironment.isHeadless then
      MenuController.start()
      MenuController.mapPathAndName.map((_, n) => n) shouldBe a[List[String]]
  }
