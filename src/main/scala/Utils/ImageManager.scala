package Utils

enum ImageManager(_path: String):
  case CAVE_FLOOR_TILE extends ImageManager("src/main/resources/img/caveFloor.png")
  case CAVE_FLOOR_TILE_2 extends ImageManager("src/main/resources/img/caveFloor2.png")
  case CHARACTER_RIGHT extends ImageManager("src/main/resources/img/HIKER_dx.png")
  case CHARACTER_LEFT extends ImageManager("src/main/resources/img/HIKER_sx.png")
  case CHARACTER_UP extends ImageManager("src/main/resources/img/HIKER_up.png")
  case CHARACTER_DOWN extends ImageManager("src/main/resources/img/HIKER_down.png")
  case BERRY extends ImageManager("src/main/resources/img/berrytree_AGUAVBERRY.png")

  /** @return
    *   the path of the image
    */
  val path: String = _path
