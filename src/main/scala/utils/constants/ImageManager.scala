package utils.constants

import utils.constants.ImageManager
import java.net.URL

enum ImageManager(_path: String):
  case CharacterRight extends ImageManager("HIKER_dx.png")
  case CharacterLeft extends ImageManager("HIKER_sx.png")
  case CharacterUp extends ImageManager("HIKER_up.png")
  case CharacterDown extends ImageManager("HIKER_down.png")
  case StartMenuBackground extends ImageManager("start_menu_image.png")
  case SelectMapBackground extends ImageManager("select_map_image.jpg")
  case Controls extends ImageManager("controls_image.png")
  case BackGround extends ImageManager("background.png")
  case ScoreBackground extends ImageManager("score_background.png")
  case PauseBackground extends ImageManager("pauseBackground.jpg")
  case Key extends ImageManager("Key.png")
  case Coin extends ImageManager("Coin.png")
  case Bag extends ImageManager("Bag.png")
  case Trunk extends ImageManager("Trunk.png")
  case Axe extends ImageManager("Axe.png")
  case Pick extends ImageManager("Pick.png")
  case Pause extends ImageManager("Pause.png")
  case Save extends ImageManager("Save.png")
  case Play extends ImageManager("Play.png")
  case End extends ImageManager("endGame.png")
  case Home extends ImageManager("home.png")

  /** @return
    *   the path of the image
    */
  val path: URL = printIt(getClass.getClassLoader.getResource("img/" + _path))

  def printIt(url: URL): URL =
    println(url)
    url
