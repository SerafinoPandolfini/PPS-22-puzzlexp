package utils

enum ImageManager(_path: String):
  case CharacterRight extends ImageManager("src/main/resources/img/HIKER_dx.png")
  case CharacterLeft extends ImageManager("src/main/resources/img/HIKER_sx.png")
  case CharacterUp extends ImageManager("src/main/resources/img/HIKER_up.png")
  case CharacterDown extends ImageManager("src/main/resources/img/HIKER_down.png")
  case StartMenuBackground extends ImageManager("src/main/resources/img/start_menu_image.png")
  case Berry extends ImageManager("src/main/resources/img/berrytree_AGUAVBERRY.png")
  case BackGround extends ImageManager("src/main/resources/img/background.png")

  /** @return
    *   the path of the image
    */
  val path: String = _path
