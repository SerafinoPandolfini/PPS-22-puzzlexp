package model.cells

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class ScoreSpec extends AnyFlatSpec with BeforeAndAfterEach:
  var scoreCounter: ScoreCounter = _

  override def beforeEach(): Unit =
    super.beforeEach()
    scoreCounter = ScoreCounter()

  "Initially the scoreCounter" should "be 0" in {
    scoreCounter.score should be(0)
  }

  "The scoreCounter" should "be able to update its value" in {
    scoreCounter = scoreCounter.copy(score = 10)
    scoreCounter.score should be(10)
  }
