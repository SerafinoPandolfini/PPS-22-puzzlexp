package controller

import controller.MenuController
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import serialization.JsonDecoder
import utils.ConstantUtils.*
import view.MenuView

import java.awt.GraphicsEnvironment

class MenuSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var controller: MenuController = _
  var view: MenuView = _

  override def beforeEach(): Unit =
    controller = MenuController()
    view = MenuView(controller)
    super.beforeEach()

  override def afterEach(): Unit =
    super.afterEach()
    if !GraphicsEnvironment.isHeadless then view.dispose()

  "a controller menu" should "be able to get all the path of the map file" in {
    if !GraphicsEnvironment.isHeadless then view.mapPathAndName.map((p, _) => p) shouldBe a[List[String]]
  }

  "a controller menu" should "be able to get all the name of the map" in {
    if !GraphicsEnvironment.isHeadless then view.mapPathAndName.map((_, n) => n) shouldBe a[List[String]]
  }

  "a controller menu" should "be able to check if a file exist" in {
    if !GraphicsEnvironment.isHeadless then
      controller.isFilePresent(JsonDecoder.getAbsolutePath(CheckExistingFile)) should be(true)
      controller.isFilePresent(JsonDecoder.getAbsolutePath(CheckWrongFile)) should be(false)

  }
