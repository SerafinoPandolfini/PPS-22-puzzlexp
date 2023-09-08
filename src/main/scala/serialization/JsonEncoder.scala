package serialization

import model.cells.Item
import model.cells.*
import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Encoder, Json}
import io.circe.syntax.*
import model.room.{Room, RoomLink}
import model.gameMap.GameMap

object JsonEncoder:
  given basicCellEncoder: Encoder[BasicCell] = deriveEncoder[BasicCell]

  given buttonCellEncoder: Encoder[ButtonCell] = deriveEncoder[ButtonCell]

  given cliffCellEncoder: Encoder[CliffCell] = deriveEncoder[CliffCell]

  given buttonBlockCellEncoder: Encoder[ButtonBlockCell] = deriveEncoder[ButtonBlockCell]

  given coveredHoleCellEncoder: Encoder[CoveredHoleCell] = deriveEncoder[CoveredHoleCell]

  given holeCellEncoder: Encoder[HoleCell] = deriveEncoder[HoleCell]

  given pressurePlateBlockCellEncoder: Encoder[PressurePlateBlockCell] = deriveEncoder[PressurePlateBlockCell]

  given pressurePlateCellEncoder: Encoder[PressurePlateCell] = deriveEncoder[PressurePlateCell]

  given teleportCellEncoder: Encoder[TeleportCell] = deriveEncoder[TeleportCell]

  given teleportDestinationCellEncoder: Encoder[TeleportDestinationCell] = deriveEncoder[TeleportDestinationCell]

  given wallCellEncoder: Encoder[WallCell] = deriveEncoder[WallCell]

  given rockCellEncoder: Encoder[RockCell] = deriveEncoder[RockCell]

  given plantCellEncoder: Encoder[PlantCell] = deriveEncoder[PlantCell]

  given lockCellEncoder: Encoder[LockCell] = deriveEncoder[LockCell]

  given cellEncoder: Encoder[Cell] = Encoder.instance {
    case basicCell: BasicCell =>
      basicCellEncoder.apply(basicCell).deepMerge(Json.obj("cellType" -> Json.fromString("BasicCell")))
    case cliffCell: CliffCell =>
      cliffCellEncoder.apply(cliffCell).deepMerge(Json.obj("cellType" -> Json.fromString("CliffCell")))
    case buttonBlockCell: ButtonBlockCell =>
      buttonBlockCellEncoder
        .apply(buttonBlockCell)
        .deepMerge(Json.obj("cellType" -> Json.fromString("ButtonBlockCell")))
    case buttonCell: ButtonCell =>
      buttonCellEncoder.apply(buttonCell).deepMerge(Json.obj("cellType" -> Json.fromString("ButtonCell")))
    case coveredHoleCell: CoveredHoleCell =>
      coveredHoleCellEncoder
        .apply(coveredHoleCell)
        .deepMerge(Json.obj("cellType" -> Json.fromString("CoveredHoleCell")))
    case holeCell: HoleCell =>
      holeCellEncoder.apply(holeCell).deepMerge(Json.obj("cellType" -> Json.fromString("HoleCell")))
    case pressurePlateBlockCell: PressurePlateBlockCell =>
      pressurePlateBlockCellEncoder
        .apply(pressurePlateBlockCell)
        .deepMerge(Json.obj("cellType" -> Json.fromString("PressurePlateBlockCell")))
    case pressurePlateCell: PressurePlateCell =>
      pressurePlateCellEncoder
        .apply(pressurePlateCell)
        .deepMerge(Json.obj("cellType" -> Json.fromString("PressurePlateCell")))
    case teleportCell: TeleportCell =>
      teleportCellEncoder.apply(teleportCell).deepMerge(Json.obj("cellType" -> Json.fromString("TeleportCell")))
    case teleportDestinationCell: TeleportDestinationCell =>
      teleportDestinationCellEncoder
        .apply(teleportDestinationCell)
        .deepMerge(Json.obj("cellType" -> Json.fromString("TeleportDestinationCell")))
    case wallCell: WallCell =>
      wallCellEncoder.apply(wallCell).deepMerge(Json.obj("cellType" -> Json.fromString("WallCell")))
    case lockCell: LockCell =>
      lockCellEncoder.apply(lockCell).deepMerge(Json.obj("cellType" -> Json.fromString("LockCell")))
    case rockCell: RockCell =>
      rockCellEncoder.apply(rockCell).deepMerge(Json.obj("cellType" -> Json.fromString("RockCell")))
    case plantCell: PlantCell =>
      plantCellEncoder.apply(plantCell).deepMerge(Json.obj("cellType" -> Json.fromString("PlantCell")))
  }

  /** parte room */
  given roomLinkEncoder: Encoder[RoomLink] = deriveEncoder[RoomLink]

  given roomEncoder: Encoder[Room] = Encoder.instance { room =>
    Json.obj(
      "name" -> room.name.asJson,
      "cells" -> room.cells.asJson,
      "links" -> room.links.asJson
    )
  }

  /** parte map */
  given mapEncoder: Encoder[GameMap] = Encoder.instance { map =>
    Json.obj(
      "name" -> map.name.asJson,
      "rooms" -> map.rooms.map(r => roomEncoder.apply(r)).asJson,
      "initialRoom" -> map.initialRoom.asJson,
      "initialPosition" -> map.initialPosition.asJson
    )
  }
