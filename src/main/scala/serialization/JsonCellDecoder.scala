package serialization

import io.circe.{Decoder, DecodingFailure}
import io.circe.generic.semiauto.deriveDecoder
import model.cells.*
import utils.constants.JsonFieldsManager.*

object JsonCellDecoder:
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

  /** decoder for all the [[Cell]]
    * @return
    *   a decoder for Cell
    */
  given cellDecoder: Decoder[Cell] = Decoder.instance(c =>
    val cellType = c.downField(CellType).as[String].getOrElse("Unknown")
    val decoder: Decoder[Cell] = cellType match
      case Basic               => mapToCell(basicCellDecoder)
      case Cliff               => mapToCell(cliffCellDecoder)
      case ButtonBlock         => mapToCell(buttonBlockCellDecoder)
      case Button              => mapToCell(buttonCellDecoder)
      case CoveredHole         => mapToCell(coveredHoleCellDecoder)
      case Hole                => mapToCell(holeCellDecoder)
      case PressurePlateBlock  => mapToCell(pressurePlateBlockCellDecoder)
      case PressurePlate       => mapToCell(pressurePlateCellDecoder)
      case Teleport            => mapToCell(teleportCellDecoder)
      case TeleportDestination => mapToCell(teleportDestinationCellDecoder)
      case Wall                => mapToCell(wallCellDecoder)
      case Rock                => mapToCell(rockCellDecoder)
      case Plant               => mapToCell(plantCellDecoder)
      case Lock                => mapToCell(lockCellDecoder)
      case _                   => Decoder.failed(DecodingFailure(s"Unknown cellType: $cellType", c.history))
    decoder(c)
  )
