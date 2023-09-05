package utils

import ConstantUtils.Point2D

/** this function is not yet implemented */
object Point2DIntConversion:
  given Conversion[Point2D, Int] = _ match
    case Point2D(x, y) => x
