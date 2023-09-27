package utils.extensions

import scala.annotation.targetName
import scala.math.BigDecimal.RoundingMode

object Percentage:
  val DecimalNumber = 2
  extension (value: Double)
    @targetName("percentage")
    /** calculates the percentage of this [[Double]] on another [[Double]] rounding the result to the 2nd decimal
      * @param total
      *   [[Double]] representing the total quantity
      * @return
      *   a [[Double]] representing the percentage
      */
    def %%(total: Double): Double =
      if total != 0 then BigDecimal((value / total) * 100).setScale(DecimalNumber, RoundingMode.HALF_UP).toDouble
      else throw new IllegalArgumentException("Cannot calculate percentage with a denominator of 0.")
