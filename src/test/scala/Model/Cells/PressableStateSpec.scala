package Model.Cells

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class PressableStateSpec extends AnyFlatSpec :

  "A Pressable state pressed" should "have toggle not pressed" in {
    PressableState.Pressed.toggle should be(PressableState.NotPressed)
  }

  "A Pressable state not pressed" should "have toggle pressed" in {
    PressableState.NotPressed.toggle should be(PressableState.Pressed)
  }

