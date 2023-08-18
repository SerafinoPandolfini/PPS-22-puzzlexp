package Serialization

import Model.Cells.*
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, DecodingFailure}
import io.circe.parser.*
import io.circe.Json
import io.circe.Error
import Model.Room.{Room, RoomLink}
import Model.GameMap.GameMap

object JsonDecoder:

  given basicCellDecoder: Decoder[BasicCell] = deriveDecoder[BasicCell]

  given buttonCellDecoder: Decoder[ButtonCell] = deriveDecoder[ButtonCell]

  given cliffCellDecoder: Decoder[CliffCell] = deriveDecoder[CliffCell]

  given buttonBlockCellDecoder: Decoder[ButtonBlockCell] = deriveDecoder[ButtonBlockCell]

  given coveredHoleCellDecoder: Decoder[CoveredHoleCell] = deriveDecoder[CoveredHoleCell]

  given holeCellDecoder: Decoder[HoleCell] = deriveDecoder[HoleCell]

  given switchBlockCellDecoder: Decoder[SwitchBlockCell] = deriveDecoder[SwitchBlockCell]

  given switchCellDecoder: Decoder[SwitchCell] = deriveDecoder[SwitchCell]

  given teleportCellDecoder: Decoder[TeleportCell] = deriveDecoder[TeleportCell]

  given teleportDestinationCellDecoder: Decoder[TeleportDestinationCell] = deriveDecoder[TeleportDestinationCell]

  given wallCellDecoder: Decoder[WallCell] = deriveDecoder[WallCell]

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
      case "SwitchBlockCell"         => mapToCell(switchBlockCellDecoder)
      case "SwitchCell"              => mapToCell(switchCellDecoder)
      case "TeleportCell"            => mapToCell(teleportCellDecoder)
      case "TeleportDestinationCell" => mapToCell(teleportDestinationCellDecoder)
      case "WallCell"                => mapToCell(wallCellDecoder)
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
    yield new Room(name, cells, links)
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
