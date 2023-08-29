package utils

enum ImageManager(_path: String):
  case CaveFloorTile extends ImageManager("src/main/resources/img/caveFloor.png")
  case CaveFloorTile2 extends ImageManager("src/main/resources/img/caveFloor2.png")
  case CharacterRight extends ImageManager("src/main/resources/img/HIKER_dx.png")
  case CharacterLeft extends ImageManager("src/main/resources/img/HIKER_sx.png")
  case CharacterUp extends ImageManager("src/main/resources/img/HIKER_up.png")
  case CharacterDown extends ImageManager("src/main/resources/img/HIKER_down.png")
  case Berry extends ImageManager("src/main/resources/img/berrytree_AGUAVBERRY.png")

  /** @return
    *   the path of the image
    */
  val path: String = _path
