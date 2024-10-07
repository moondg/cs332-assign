package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times([])") {
    assert(times(List()) === List())
  }

  test("times(['c', 'a', 'b', 'a', 'c'])") {
    assert(times(List('c', 'a', 'b', 'a', 'c')).sortBy(_._1) === List(('a', 2), ('b', 1), ('c', 2)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("combine of some leaf list, needs sort") {
    val leaflist = List(Leaf('a', 2), Leaf('b', 3), Leaf('c', 4), Leaf('d', 7))
    val forkAB = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    assert(combine(leaflist) === List(Leaf('c', 4), forkAB, Leaf('d', 7)))
  }

  test("createCodeTree") {
    val charlist = List('a', 'b', 'a', 'b', 'c', 'd', 'b', 'c', 'c', 'c', 'd', 'd', 'd', 'd', 'd', 'd')
    val leaflist = List(Leaf('a', 2), Leaf('b', 3), Leaf('c', 4), Leaf('d', 7))
    val forkAB = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val forkABC = Fork(Leaf('c', 4), forkAB, List('c', 'a', 'b'), 9)
    assert(createCodeTree(charlist) === Fork(Leaf('d', 7), forkABC, List('d', 'c', 'a', 'b'), 16))
  }

  test("decode") {
    new TestTrees {
      assert(decode(t2, List(0, 0, 0, 1, 1, 0, 0, 1)) === List('a', 'b', 'd', 'a', 'd'))
    }
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("codeBits") {
    val table = List(('a', List(0, 0)), ('b', List(0, 1)), ('c', List(1)))
    assert(codeBits(table)('b') === List(0, 1))
  }

  test("convert") {
    new TestTrees {
      assert(convert(t2).sortBy(_._1) === List(('a', List(0, 0)), ('b', List(0, 1)), ('d', List(1))))
    }
  }

  test("quick decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, quickEncode(t1)("ab".toList)) === "ab".toList)
    }
  }
}
