package utils

import scala.annotation.targetName

object Percentage:
  extension (value: Double)
    @targetName("percentage")
    def %%(total: Double): Double =
      if total != 0 then (value / total) * 100
      else throw new IllegalArgumentException("Cannot calculate percentage with a denominator of 0.")
