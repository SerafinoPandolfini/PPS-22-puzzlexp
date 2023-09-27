package serialization

import model.cells.properties.{Direction, Item}
import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Encoder, Json}
import io.circe.syntax.*
import model.game.CurrentGame
import model.room.{Room, RoomLink}
import model.gameMap.{GameMap, MinimapElement}
import serialization.JsonCellEncoder.cellEncoder
import utils.constants.JsonFieldsManager.*

object JsonEncoder:

  given itemEncoder: Encoder[Item] = deriveEncoder[Item]

  given roomLinkEncoder: Encoder[RoomLink] = deriveEncoder[RoomLink]

  given directionEncoder: Encoder[Direction] = deriveEncoder[Direction]

  given minimapElementEncoder: Encoder[MinimapElement] = deriveEncoder[MinimapElement]

  /** encoder for [[Room]]
    * @return
    *   an encoder for room
    */
  given roomEncoder: Encoder[Room] = Encoder.instance(room =>
    Json.obj(
      RoomName -> room.name.asJson,
      RoomCells -> room.cells.asJson,
      RoomLinks -> room.links.asJson
    )
  )

  /** encoder for [[GameMap]]
    * @return
    *   an encoder for GameMap
    */
  given mapEncoder: Encoder[GameMap] = Encoder.instance(map =>
    Json.obj(
      MapName -> map.name.asJson,
      MapRooms -> map.rooms.map(r => roomEncoder.apply(r)).asJson,
      MapInitialRoom -> map.initialRoom.asJson,
      MapInitialPosition -> map.initialPosition.asJson
    )
  )

  /** an encoder for the game to save
    * @return
    *   an encoder for the save game file
    */
  given saveGameEncoder: Encoder[Unit] = Encoder.instance(_ =>
    Json.obj(
      SaveMapName -> CurrentGame.originalGameMap.name.asJson,
      SaveMap -> mapEncoder.apply(CurrentGame.gameMap).asJson,
      SaveRoom -> roomEncoder.apply(CurrentGame.currentRoom).asJson,
      SaveCurrentPosition -> CurrentGame.currentPosition.asJson,
      SaveStartPosition -> CurrentGame.startPositionInRoom.asJson,
      SaveItems -> CurrentGame.itemHolder.itemOwned.asJson,
      SaveScore -> CurrentGame.scoreCounter.asJson,
      SaveMiniMap -> CurrentGame.minimapElement.asJson
    )
  )
