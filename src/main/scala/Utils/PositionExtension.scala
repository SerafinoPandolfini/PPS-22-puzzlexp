package Utils

import Model.Cells.Position
import scala.annotation.targetName

object PositionExtension:
  extension (p: Position)
    @targetName("sumAlias")
    def +(q: Position): Position = (p._1 + q._1, p._2 + q._2)

