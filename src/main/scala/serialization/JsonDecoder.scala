package serialization

import model.cells.*
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, DecodingFailure}
import io.circe.parser.*
import io.circe.Json
import io.circe.Error
import model.game.CurrentGame
import model.room.{Room, RoomLink}
import model.gameMap.GameMap

type SaveData = (String, GameMap, Room, Position, Position, List[Item], Int)

object JsonDecoder:

  given decoderItem: Decoder[Item] = deriveDecoder[Item]

  given basicCellDecoder: Decoder[BasicCell] = deriveDecoder[BasicCell]

  given buttonCellDecoder: Decoder[ButtonCell] = deriveDecoder[ButtonCell]

  given cliffCellDecoder: Decoder[CliffCell] = deriveDecoder[CliffCell]

  given buttonBlockCellDecoder: Decoder[ButtonBlockCell] = deriveDecoder[ButtonBlockCell]

  given coveredHoleCellDecoder: Decoder[CoveredHoleCell] = deriveDecoder[CoveredHoleCell]

  given holeCellDecoder: Decoder[HoleCell] = deriveDecoder[HoleCell]

  given pressurePlateBlockCellDecoder: Decoder[PressurePlateBlockCell] = deriveDecoder[PressurePlateBlockCell]

  given pressurePlateCellDecoder: Decoder[PressurePlateCell] = deriveDecoder[PressurePlateCell]

  given teleportCellDecoder: Decoder[TeleportCell] = deriveDecoder[TeleportCell]

  given teleportDestinationCellDecoder: Decoder[TeleportDestinationCell] = deriveDecoder[TeleportDestinationCell]

  given wallCellDecoder: Decoder[WallCell] = deriveDecoder[WallCell]

  given rockCellDecoder: Decoder[RockCell] = deriveDecoder[RockCell]

  given plantCellDecoder: Decoder[PlantCell] = deriveDecoder[PlantCell]

  given lockCellDecoder: Decoder[LockCell] = deriveDecoder[LockCell]

  private def mapToCell[A <: Cell](decoder: Decoder[A]): Decoder[Cell] =
    decoder.map(identity)

  given cellDecoder: Decoder[Cell] = Decoder.instance { c =>
    val cellType = c.downField("cellType").as[String].getOrElse("Unknown")
    val decoder: Decoder[Cell] = cellType match
      case "BasicCell"               => mapToCell(basicCellDecoder)
      case "CliffCell"               => mapToCell(cliffCellDecoder)
      case "ButtonBlockCell"         => mapToCell(buttonBlockCellDecoder)
      case "ButtonCell"              => mapToCell(buttonCellDecoder)
      case "CoveredHoleCell"         => mapToCell(coveredHoleCellDecoder)
      case "HoleCell"                => mapToCell(holeCellDecoder)
      case "PressurePlateBlockCell"  => mapToCell(pressurePlateBlockCellDecoder)
      case "PressurePlateCell"       => mapToCell(pressurePlateCellDecoder)
      case "TeleportCell"            => mapToCell(teleportCellDecoder)
      case "TeleportDestinationCell" => mapToCell(teleportDestinationCellDecoder)
      case "WallCell"                => mapToCell(wallCellDecoder)
      case "RockCell"                => mapToCell(rockCellDecoder)
      case "PlantCell"               => mapToCell(plantCellDecoder)
      case "LockCell"                => mapToCell(lockCellDecoder)
      case _                         => Decoder.failed(DecodingFailure(s"Unknown cellType: $cellType", c.history))
    decoder(c)
  }

  /** parte room */

  given roomLinkDecoder: Decoder[RoomLink] = deriveDecoder[RoomLink]

  given roomDecoder: Decoder[Room] = Decoder.instance { cursor =>
    for
      name <- cursor.downField("name").as[String]
      cells <- cursor.downField("cells").as[Set[Cell]]
      links <- cursor.downField("links").as[Set[RoomLink]]
    yield Room(name, cells, links)
  }

  /** parte map */
  given mapDecoder: Decoder[GameMap] = Decoder.instance { cursor =>
    for
      name <- cursor.downField("name").as[String]
      rooms <- cursor.downField("rooms").as[Set[Room]]
      initialRoom <- cursor.downField("initialRoom").as[String]
      initialPosition <- cursor.downField("initialPosition").as[Position]
    yield new GameMap(name, rooms, initialRoom, initialPosition)

  }

  def getAbsolutePath(partialPath: String): String =
    import java.nio.file.Paths
    Paths.get(partialPath).toAbsolutePath.toString

  def getJsonFromPath(filePath: String): Either[Error, Json] =
    import scala.io.Source
    val source = Source.fromFile(filePath)
    try
      val jsonString = source.mkString
      parse(jsonString)
    finally source.close()

  given saveGameDecoder: Decoder[SaveData] = Decoder.instance { cursor =>
    for
      originalMap <- cursor.downField("mapName").as[String]
      currentMap <- cursor.downField("map").as[GameMap]
      currentRoom <- cursor.downField("room").as[Room]
      currentPlayerPosition <- cursor.downField("currentPos").as[Position]
      startPlayerPosition <- cursor.downField("startPos").as[Position]
      itemList <- cursor.downField("items").as[List[Item]]
      score <- cursor.downField("score").as[Int]
    yield (
      getAbsolutePath("src/main/resources/json/" + originalMap + ".json"),
      currentMap,
      currentRoom,
      currentPlayerPosition,
      startPlayerPosition,
      itemList,
      score
    )
  }
