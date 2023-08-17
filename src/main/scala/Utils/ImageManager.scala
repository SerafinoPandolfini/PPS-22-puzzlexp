package Utils

enum ImageManager(_path: String):
  case CAVE_FLOOR_TILE extends ImageManager("src/main/resources/img/caveFloor.png")
  case CAVE_FLOOR_TILE_2 extends ImageManager("src/main/resources/img/caveFloor2.png")
  case CHARACTER_RIGHT extends ImageManager("src/main/resources/img/HIKER_dx.png")

  /** @return
    *   the path of the image
    */
  val path: String = _path
