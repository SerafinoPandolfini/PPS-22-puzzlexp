package model.gameMap

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.GivenWhenThen
import Minimap.createMinimap
import scala.util.Success
import serialization.JsonDecoder
import utils.TestUtils.{defaultPosition, position1_0, position0_1, position1_1}

class MinimapSpec extends AnyFlatSpec with BeforeAndAfterEach with GivenWhenThen:

  var mapFiles = List("json/testMap.json", "json/cyclicMap.json")
  var maps = List.empty[GameMap]

  override def beforeEach(): Unit =
    mapFiles.foreach(
      JsonDecoder.getJsonFromPath(_) match
        case Success(jsonData) =>
          JsonDecoder.mapDecoder(jsonData.hcursor) match
            case Right(m) => maps = maps :+ m
            case _        => ()
        case _ => ()
    )

  "A Minimap" should "represent every room of the map" in {
    Given("a list of GameMaps")
    maps.foreach(map => {
      When("a minimap is created for map " + map.name)
      val minimap = map.createMinimap()
      Then("the size should match the map's room's size")
      minimap.size should be(map.rooms.size)
      And("the name of the minimapElements should match the room's names")
      minimap.foreach(m => map.rooms.filter(r => r.name == m.name).map(r => r.name).contains(m.name) should be(true))
      And("the direction of the minimapElements should match the roomLink's directions")
      minimap.foreach(m =>
        map.rooms
          .filter(r => r.name == m.name)
          .map(r => r.links.map(l => l.direction))
          .contains(m.directions) should be(
          true
        )
      )
    })
  }

  "A Minimap" should "have the positions of its elements in a non negative range" in {
    Given("a minimap")
    val minimap = maps.head.createMinimap()
    Then("its positions should be non negative")
    minimap.foreach(e => e.position._1 >= 0 && e.position._2 >= 0 should be(true))
    And("there should not be any overlapping between them")
    minimap.map(_.position).toSet.size should be(minimap.size)
  }


