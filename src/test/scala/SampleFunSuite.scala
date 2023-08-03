import org.scalatest.funsuite.AnyFunSuite

import scala.runtime.stdLibPatches.Predef.assert


class SampleFunSuite extends AnyFunSuite {

  val lst = List.empty[String]
  val arr = Array("foo", "bar")

  test("test list methods") {
    assert(lst.size == 0)
  }

  test("test array methods") {
    assert(arr(0) == "foo")
    assertThrows[ArrayIndexOutOfBoundsException] {
      arr(13)
    }
  }
}
