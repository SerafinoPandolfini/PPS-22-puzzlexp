package controller.menu

import controller.menu.MenuController
import view.menu.MenuView
import utils.constants.PathManager.*
import utils.constants.PlayableMaps.getPlayableMaps

import java.io.{File, InputStream}
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
    (for
      map <- getPlayableMaps
      resource = map.replace(" ", "") + JsonExtension
    yield map -> resource).to(ListMap)

  /** check if the @param path is present
    * @return
    *   true if the file exists
    */
  def isFilePresent(path: String): Boolean =
    val file: File = File(path)
    file.exists()

  def isInternalFilePresent(path: String): Boolean =
    Option(getClass.getClassLoader.getResource(JsonDirectoryPath + path)).isDefined
