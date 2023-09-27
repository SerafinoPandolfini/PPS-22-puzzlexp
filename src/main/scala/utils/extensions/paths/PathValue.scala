package utils.extensions.paths

object PathValue:

  val PathStart: String = "cell"
  val PathSplit: String = "_"
  val PathWalkable: String = "_W"
  val PathDeadly: String = "_D"
  val PathCovered: String = "_C"
  val PathPressed: String = "_P"
  val NoPath: String = ""
  val lowerAdjacentBound: Int = 0
  val upperAdjacentBound: Int = 2
  val offsetFactor: Int = 1
  val validCorners: List[List[(Int, Int)]] = List(
    List((0, 0), (0, 1), (1, 0)),
    List((1, 0), (2, 0), (2, 1)),
    List((0, 1), (0, 2), (1, 2)),
    List((1, 2), (2, 1), (2, 2))
  )
