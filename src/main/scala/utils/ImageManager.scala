package utils

enum ImageManager(_path: String):
  case CharacterRight extends ImageManager("src/main/resources/img/HIKER_dx.png")
  case CharacterLeft extends ImageManager("src/main/resources/img/HIKER_sx.png")
  case CharacterUp extends ImageManager("src/main/resources/img/HIKER_up.png")
  case CharacterDown extends ImageManager("src/main/resources/img/HIKER_down.png")
  case BackGround extends ImageManager("src/main/resources/img/background.png")
  case Key extends ImageManager("src/main/resources/img/Key.png")
  case Coin extends ImageManager("src/main/resources/img/Coin.png")
  case Bag extends ImageManager("src/main/resources/img/Bag.png")
  case Trunk extends ImageManager("src/main/resources/img/Trunk.png")
  case Axe extends ImageManager("src/main/resources/img/Axe.png")
  case Pick extends ImageManager("src/main/resources/img/Pick.png")

  /** @return
    *   the path of the image
    */
  val path: String = _path
