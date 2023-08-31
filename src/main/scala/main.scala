import model.gameMap.GameMap
import model.room.{Room, RoomBuilder, RoomLink}
import model.cells.{BasicCell, Direction, HoleCell, Item}
import serialization.{JsonDecoder, JsonEncoder}

@main
def main(): Unit =
  val map = start
  var currentRoom: Room = map.getRoomFromName(map.initialRoom).get
  var currentPosition = map.initialPosition
  while (!currentRoom.isPlayerDead(currentPosition).toOption.get)
    println(currentRoom.cellsRepresentation(Room.showPlayerAndBoxes(currentPosition)))
    println("where to go? left/right/up/down")
    val name = scala.io.StdIn.readLine()
    val dir = name match
      case "left"  => Direction.Left
      case "right" => Direction.Right
      case "up"    => Direction.Up
      case "down"  => Direction.Down

    currentRoom.playerMove(currentPosition, dir) match
      case Some(value) => currentPosition = value
      case None =>
        val tup = map.changeRoom(currentPosition, currentRoom.name, dir).get
        currentRoom = tup._1
        currentPosition = tup._2
  println("GAME-OVER :(")

def start: GameMap =
  val p = JsonDecoder.getAbsolutePath("src/main/resources/json/mapProva.json")
  JsonDecoder.mapDecoder(JsonDecoder.getJsonFromPath(p).toOption.get.hcursor).toOption.get

@main
def generate(): Unit =
  val room = RoomBuilder()
    .borderWalls()
    .addLinks(RoomLink((0, 3), Direction.Left, "TestRoom2", (24, 3)))
    .name("TestRoom1")
    .standardize
    .build
  val room2 = RoomBuilder()
    .borderWalls()
    .addLinks(RoomLink((24, 3), Direction.Right, "TestRoom1", (0, 3)))
    .name("TestRoom2")
    .standardize
    .build
  val map = new GameMap("testMap", Set(room, room2), "TestRoom1", (1, 1))
  println(JsonEncoder.mapEncoder(map))
