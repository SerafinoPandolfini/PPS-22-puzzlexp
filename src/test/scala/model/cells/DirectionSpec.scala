package model.cells

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class DirectionSpec extends AnyFlatSpec :

  "A Direction right" should "have opposite left" in {
    Direction.Right.opposite should be(Direction.Left)
  }

  "A Direction left" should "have opposite right" in {
    Direction.Left.opposite should be(Direction.Right)
  }

  "A Direction up" should "have opposite down" in {
    Direction.Up.opposite should be(Direction.Down)
  }

  "A Direction down" should "have opposite up" in {
    Direction.Down.opposite should be(Direction.Up)
  }



