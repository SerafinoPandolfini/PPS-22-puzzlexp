package controller

import controller.MenuController
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import serialization.JsonDecoder
import utils.TestUtils.*
import view.MenuView

import java.awt.GraphicsEnvironment
import java.nio.file.Paths

class MenuSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var controller: MenuController = _
  var view: MenuView = _

  override def beforeEach(): Unit =
    if !GraphicsEnvironment.isHeadless then
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
      controller.isFilePresent(CheckExistingFile) should be(true)
      controller.isFilePresent(CheckWrongFile) should be(false)
  }
