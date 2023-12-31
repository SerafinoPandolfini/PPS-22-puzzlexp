package serialization

import io.circe.{Encoder, Json}
import io.circe.generic.semiauto.deriveEncoder
import model.cells.*
import utils.constants.JsonFieldsManager.*

object JsonCellEncoder:
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

  /** encoder for [[Cell]]
    * @return
    *   an encoder for [[Cell]]
    */
  given cellEncoder: Encoder[Cell] = Encoder.instance {
    case basicCell: BasicCell =>
      basicCellEncoder.apply(basicCell).deepMerge(Json.obj(CellType -> Json.fromString(Basic)))
    case cliffCell: CliffCell =>
      cliffCellEncoder.apply(cliffCell).deepMerge(Json.obj(CellType -> Json.fromString(Cliff)))
    case buttonBlockCell: ButtonBlockCell =>
      buttonBlockCellEncoder
        .apply(buttonBlockCell)
        .deepMerge(Json.obj(CellType -> Json.fromString(ButtonBlock)))
    case buttonCell: ButtonCell =>
      buttonCellEncoder.apply(buttonCell).deepMerge(Json.obj(CellType -> Json.fromString(Button)))
    case coveredHoleCell: CoveredHoleCell =>
      coveredHoleCellEncoder
        .apply(coveredHoleCell)
        .deepMerge(Json.obj(CellType -> Json.fromString(CoveredHole)))
    case holeCell: HoleCell =>
      holeCellEncoder.apply(holeCell).deepMerge(Json.obj(CellType -> Json.fromString(Hole)))
    case pressurePlateBlockCell: PressurePlateBlockCell =>
      pressurePlateBlockCellEncoder
        .apply(pressurePlateBlockCell)
        .deepMerge(Json.obj(CellType -> Json.fromString(PressurePlateBlock)))
    case pressurePlateCell: PressurePlateCell =>
      pressurePlateCellEncoder
        .apply(pressurePlateCell)
        .deepMerge(Json.obj(CellType -> Json.fromString(PressurePlate)))
    case teleportCell: TeleportCell =>
      teleportCellEncoder.apply(teleportCell).deepMerge(Json.obj(CellType -> Json.fromString(Teleport)))
    case teleportDestinationCell: TeleportDestinationCell =>
      teleportDestinationCellEncoder
        .apply(teleportDestinationCell)
        .deepMerge(Json.obj(CellType -> Json.fromString(TeleportDestination)))
    case wallCell: WallCell =>
      wallCellEncoder.apply(wallCell).deepMerge(Json.obj(CellType -> Json.fromString(Wall)))
    case lockCell: LockCell =>
      lockCellEncoder.apply(lockCell).deepMerge(Json.obj(CellType -> Json.fromString(Lock)))
    case rockCell: RockCell =>
      rockCellEncoder.apply(rockCell).deepMerge(Json.obj(CellType -> Json.fromString(Rock)))
    case plantCell: PlantCell =>
      plantCellEncoder.apply(plantCell).deepMerge(Json.obj(CellType -> Json.fromString(Plant)))
  }
