package utils.extensions

import scala.annotation.targetName
import scala.math.BigDecimal.RoundingMode

object Percentage:
  extension (value: Double)
    @targetName("percentage")
    def %%(total: Double): Double =
      if total != 0 then BigDecimal((value / total) * 100).setScale(2, RoundingMode.HALF_UP).toDouble
      else throw new IllegalArgumentException("Cannot calculate percentage with a denominator of 0.")
