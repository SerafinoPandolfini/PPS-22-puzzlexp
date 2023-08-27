<<<<<<<< HEAD:src/main/scala/model/cells/extension/PositionExtension.scala
package model.cells.extension
========
package utils
>>>>>>>> develop:src/main/scala/utils/PositionExtension.scala

import model.cells.Position

import scala.annotation.targetName

object PositionExtension:
  /** extension for Position for adding operations */
  extension (p: Position)
    @targetName("sumAlias")
    /** @param q
      *   the Position to sum
      * @return
      *   the sum of the positions
      */
    def +(q: Position): Position = (p._1 + q._1, p._2 + q._2)
