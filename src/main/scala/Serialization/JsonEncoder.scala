package Serialization

import Model.Cells.*
import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Encoder, Json}
import io.circe.syntax.*
import Model.Room.{Room, RoomLink}

object JsonEncoder:
  given basicCellEncoder: Encoder[BasicCell] = deriveEncoder[BasicCell]

  given buttonCellEncoder: Encoder[ButtonCell] = deriveEncoder[ButtonCell]

  given cliffCellEncoder: Encoder[CliffCell] = deriveEncoder[CliffCell]

  given buttonBlockCellEncoder: Encoder[ButtonBlockCell] = deriveEncoder[ButtonBlockCell]

  given coveredHoleCellEncoder: Encoder[CoveredHoleCell] = deriveEncoder[CoveredHoleCell]

  given holeCellEncoder: Encoder[HoleCell] = deriveEncoder[HoleCell]

  given switchBlockCellEncoder: Encoder[SwitchBlockCell] = deriveEncoder[SwitchBlockCell]

  given switchCellEncoder: Encoder[SwitchCell] = deriveEncoder[SwitchCell]

  given teleportCellEncoder: Encoder[TeleportCell] = deriveEncoder[TeleportCell]

  given teleportDestinationCellEncoder: Encoder[TeleportDestinationCell] = deriveEncoder[TeleportDestinationCell]

  given wallCellEncoder: Encoder[WallCell] = deriveEncoder[WallCell]

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
    case switchBlockCell: SwitchBlockCell =>
      switchBlockCellEncoder
        .apply(switchBlockCell)
        .deepMerge(Json.obj("cellType" -> Json.fromString("SwitchBlockCell")))
    case switchCell: SwitchCell =>
      switchCellEncoder.apply(switchCell).deepMerge(Json.obj("cellType" -> Json.fromString("SwitchCell")))
    case teleportCell: TeleportCell =>
      teleportCellEncoder.apply(teleportCell).deepMerge(Json.obj("cellType" -> Json.fromString("TeleportCell")))
    case teleportDestinationCell: TeleportDestinationCell =>
      teleportDestinationCellEncoder
        .apply(teleportDestinationCell)
        .deepMerge(Json.obj("cellType" -> Json.fromString("TeleportDestinationCell")))
    case wallCell: WallCell =>
      wallCellEncoder.apply(wallCell).deepMerge(Json.obj("cellType" -> Json.fromString("WallCell")))
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
