package controller.menu

import controller.menu.MenuController
import exceptions.MapNotFoundException
import org.json4s.*
import org.json4s.native.JsonMethods.*
import utils.constants.GraphicManager.JsonDirectoryPath
import view.menu.MenuView

import java.io.File
import scala.collection.immutable.{List, ListMap}
import scala.io.Source
import scala.util.{Try, Using}

case class MenuController():
  /** start the GUI of the menu */
  def start(): Unit =
    MenuView(this)

  /** search in JsonDirectoryPath all the map files
    * @return
    *   a list with the tuple path of the map file and name of the map
    */
  def searchMapFiles(): ListMap[String, String] =
    val jsonDirectory: File = File(JsonDirectoryPath)
    val jsonFiles = jsonDirectory.listFiles.filter(_.isFile).toList
    (for
      file <- jsonFiles
      jsonString <- Using(Source.fromFile(file))(source => source.mkString).toOption
      json <- Try(parse(jsonString)).toOption
      nameField <- json \ "name" match
        case JString(name) => Some(name)
        case _             => throw new MapNotFoundException
    yield (JsonDirectoryPath + file.getName) -> nameField).to(ListMap)

  /** check if the @param path is present
    * @return
    *   true if the file exists
    */
  def isFilePresent(path: String): Boolean =
    val file: File = File(path)
    file.exists()

