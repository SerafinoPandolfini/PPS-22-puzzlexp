package menu

import menu.MenuController.continue
import menu.MenuView

import scala.io.Source
import java.io.File
import org.json4s.*
import org.json4s.native.JsonMethods.*
import utils.ConstantUtils.JsonDirectoryPath
import scala.util.Try
import scala.collection.immutable.{List, ListMap}
import exceptions.MapNotFoundException

import scala.util.Using

object MenuController:
  private var _view: MenuView = _
  private var _continue: Boolean = false
  private var _mapPathAndName: ListMap[String, String] = _

  /** return the MenuView */
  def view: MenuView = _view

  /** return true if the player started the game */
  def continue: Boolean = _continue
  def continue_=(value: Boolean): Unit = _continue = value

  /** return the list with the tuple path of the map file and name of the map */
  def mapPathAndName: ListMap[String, String] = _mapPathAndName

  def start(): Unit =
    searchMapFile()
    _view = MenuView(continue, mapPathAndName)

  /** search in JsonDirectoryPath all the map files
    * @return
    *   a list with the tuple path of the map file and name of the map
    */
  private def searchMapFile(): Unit =
    val jsonDirectory: File = File(JsonDirectoryPath)
    val jsonFiles = jsonDirectory.listFiles.filter(_.isFile).toList
    _mapPathAndName = (for
      file <- jsonFiles
      jsonString <- Using(Source.fromFile(file))(source => source.mkString).toOption
      json <- Try(parse(jsonString)).toOption
      nameField <- json \ "name" match
        case JString(name) => Some(name)
        case _             => throw new MapNotFoundException
    yield (JsonDirectoryPath + file.getName) -> nameField).to(ListMap)

object Start extends App:
  MenuController.start()
