package serialization

import model.cells.properties.{Direction, Item}
import model.cells.{Cell, Position}
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, DecodingFailure, Json}
import io.circe.parser.*
import serialization.JsonCellDecoder.{cellDecoder, mapToCell}
import model.room.{Room, RoomLink}
import model.gameMap.{GameMap, MinimapElement}
import utils.constants.PathManager.{JsonDirectoryPath, JsonExtension, SavePath}
import utils.constants.JsonFieldsManager.*
import scala.io.{BufferedSource, Source}
import scala.util.{Try, Using}

type SaveData = (String, GameMap, Room, Position, Position, List[Item], Int, List[MinimapElement])

object JsonDecoder:

  given decoderItem: Decoder[Item] = deriveDecoder[Item]

  given roomLinkDecoder: Decoder[RoomLink] = deriveDecoder[RoomLink]

  given directionDecoder: Decoder[Direction] = deriveDecoder[Direction]

  given minimapElementDecoder: Decoder[MinimapElement] = deriveDecoder[MinimapElement]

  /** a decoder for [[Room]]
    * @return
    *   a room decoder
    */
  given roomDecoder: Decoder[Room] = Decoder.instance(cursor =>
    for
      name <- cursor.downField(RoomName).as[String]
      cells <- cursor.downField(RoomCells).as[Set[Cell]]
      links <- cursor.downField(RoomLinks).as[Set[RoomLink]]
    yield Room(name, cells, links)
  )

  /** a decoder for [[GameMap]]
    * @return
    *   a map decoder
    */
  given mapDecoder: Decoder[GameMap] = Decoder.instance(cursor =>
    for
      name <- cursor.downField(MapName).as[String]
      rooms <- cursor.downField(MapRooms).as[Set[Room]]
      initialRoom <- cursor.downField(MapInitialRoom).as[String]
      initialPosition <- cursor.downField(MapInitialPosition).as[Position]
    yield GameMap(name, rooms, initialRoom, initialPosition)
  )

  /** obtain a json from a specified path
    * @param filePath
    *   a [[String]] representing the path of the json
    * @return
    *   the [[Json]] if present, an exception otherwise
    */
  def getJsonFromPath(filePath: String): Try[Json] =
    val source = filePath match
      case externalPath if externalPath.contains(SavePath) =>
        Source.fromFile(externalPath)
      case internalPath =>
        val classLoader = getClass.getClassLoader
        val inputStream = classLoader.getResourceAsStream(internalPath)
        Source.fromInputStream(inputStream)
    Using(source)(source =>
      val jsonString = source.mkString
      parse(jsonString).getOrElse(throw new Exception("Parsing failed"))
    )

  /** a decoder for a save game file
    * @return
    *   a decoder for a save game file
    */
  given saveGameDecoder: Decoder[SaveData] = Decoder.instance(cursor =>
    for
      originalMap <- cursor.downField(SaveMapName).as[String]
      currentMap <- cursor.downField(SaveMap).as[GameMap]
      currentRoom <- cursor.downField(SaveRoom).as[Room]
      currentPlayerPosition <- cursor.downField(SaveCurrentPosition).as[Position]
      startPlayerPosition <- cursor.downField(SaveStartPosition).as[Position]
      itemList <- cursor.downField(SaveItems).as[List[Item]]
      score <- cursor.downField(SaveScore).as[Int]
      minimap <- cursor.downField(SaveMiniMap).as[List[MinimapElement]]
    yield (
      JsonDirectoryPath + originalMap + JsonExtension,
      currentMap,
      currentRoom,
      currentPlayerPosition,
      startPlayerPosition,
      itemList,
      score,
      minimap
    )
  )
