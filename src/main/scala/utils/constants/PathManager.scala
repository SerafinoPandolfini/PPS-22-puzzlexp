package utils.constants

import java.nio.file.{Path, Paths}

object PathManager:
  val JsonDirectoryPath: String = "json/"
  val JsonExtension: String = ".json"
  val ImagePath = "img/"
  val PngExtension = ".png"
  val TheoryDirectoryPath: String = "/prologTheory/"
  val PrologExtension = ".pl"
  val SavePath = "saves"
  val PathSaveGame: Path = Paths.get(System.getProperty("user.home"), "puzzlexp", SavePath)
