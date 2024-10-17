package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) { case (block, move) => move match {
        case Left => block.left
        case Right => block.right
        case Up => block.up
        case Down => block.down
      }
    }
  }

  trait Level1 extends SolutionChecker {
      /* terrain for level 1*/

    val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }

  trait Level3 extends SolutionChecker {
    val level =
    """------ooooooo--
      |oooo--ooo--oo--
      |ooooooooo--oooo
      |oSoo-------ooTo
      |oooo-------oooo
      |------------ooo""".stripMargin

    val optsolution = List(Right, Up, Right, Right, Right, Up, Left, Down, Right, Up, Up, Right, Right, Right, Down, Down, Down, Right, Up)
  }

  trait Level4 extends SolutionChecker {
    val level =
    """---ooooooo----
      |---ooooooo----
      |oooo-----ooo--
      |ooo-------oo--
      |ooo-------oo--
      |oSo--ooooooooo
      |ooo--ooooooooo
      |-----oTo--oooo
      |-----ooo------""".stripMargin

    val optsolution = List(Up, Left, Up, Right, Right, Up, Right, Right, Right, Right, Right, Right, Down, Right, Down, Down, Down, Down, Down, Right, Up, Left, Left, Left, Left, Left, Left, Down)
  }

  trait Level6 extends SolutionChecker {
    val level =
    """-----oooooo----
      |-----o--ooo----
      |-----o--ooooo--
      |Sooooo-----oooo
      |----ooo----ooTo
      |----ooo-----ooo
      |------o--oo----
      |------ooooo----
      |------ooooo----
      |-------ooo-----""".stripMargin

    val optsolution = List(Right, Right, Right, Down, Right, Down, Down, Right, Down, Down, Right, Up, Left, Left, Left, Up, Up, Left, Up, Up, Up, Right, Right, Right, Down, Down, Left, Up, Right, Right, Down, Right, Down, Down, Right)
  }

  trait Level11 extends SolutionChecker {
    val level =
    """-oooo-------
      |-oToo-------
      |oooo--------
      |-o---oooooo-
      |-o---oo--oo-
      |Soooooo--ooo
      |-----o-----o
      |-----oooo--o
      |-----ooooooo
      |--------ooo-""".stripMargin

    val optsolution = List(Right, Right, Right, Right, Up, Left, Down, Down, Down, Right, Right, Right, Down, Left, Up, Left, Left, Left, Up, Up, Right, Up, Right, Right, Down, Right, Up, Left, Left, Left, Down, Down, Left, Left, Left, Up, Up, Right, Up, Up, Left, Down, Right, Up, Right, Down, Left)
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0,0)), "0,0")
      assert(!terrain(Pos(4,11)), "4,11")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1,1))
    }
  }

  test("isStanding") {
    new Level1 {
      assert(Block(Pos(1, 1), Pos(1, 2)).isStanding == false)
      assert(Block(Pos(1, 2), Pos(1, 2)).isStanding == true)
    }
  }

  test("isLegal") {
    new Level1 {
      assert(Block(Pos(3, 1), Pos(4, 1)).isLegal == false)
      assert(Block(Pos(1, 5), Pos(1, 6)).isLegal == false)
      assert(Block(Pos(0, 4), Pos(0, 5)).isLegal == false)
      assert(Block(Pos(5, 7), Pos(5, 8)).isLegal == true)
    }
  }

  test("legalNeighbor") {
    new Level1 {
      val rightNeighbor = (Block(Pos(3, 2), Pos(3, 3)), Right)
      val upNeighbor = (Block(Pos(1, 1), Pos(2, 1)), Up)
      val legalNeighborSet = Set(rightNeighbor, upNeighbor)
      assert(Block(Pos(3, 1), Pos(3, 1)).legalNeighbors.toSet == legalNeighborSet)
    }
  }

  test("neighbor with history") {
    new Level1 {
      val block = Block(Pos(1, 1), Pos(1, 1))
      val history = List(Left, Up)
      val expected = Stream(
        (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
        (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up)))
      assert(neighborsWithHistory(block, history) == expected)
    }
  }

  test("new neighbors only") {
    new Level1 {
      val block1 = Block(Pos(1, 2), Pos(1, 3))
      val block2 = Block(Pos(1, 1), Pos(1, 1))
      val explored = Set(block1, block2)
      val neighbors = Stream(
        (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
        (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up)))
      assert(newNeighborsOnly(neighbors, explored) == neighbors.tail)
    }
  }

  test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }

  test("optimal solution for level 3") {
    new Level3 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 3") {
    new Level3 {
      assert(solution.length == optsolution.length)
    }
  }

  test("optimal solution for level 4") {
    new Level4 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 4") {
    new Level4 {
      assert(solution.length == optsolution.length)
    }
  }

  test("optimal solution for level 6") {
    new Level6 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 6") {
    new Level6 {
      assert(solution.length == optsolution.length)
    }
  }

  test("optimal solution for level 11") {
    new Level11 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 11") {
    new Level11 {
      assert(solution.length == optsolution.length)
    }
  }
}
