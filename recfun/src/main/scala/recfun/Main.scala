package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    if (c == 0 || r == 0 || c == r) 1
    else pascal(c,r-1)+pascal(c-1,r-1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def isBalance(parens: List[Char], lparenStackSize: Int): Boolean = {
      if (parens.isEmpty) lparenStackSize == 0
      else if (parens.head == '(') isBalance(parens.tail, lparenStackSize + 1)
      else if (lparenStackSize == 0) false
      else isBalance(parens.tail, lparenStackSize - 1)
    }

    isBalance(chars.filter(x => x == '(' || x == ')'), 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0) 0
    else if (coins.isEmpty) if (money == 0) 1 else 0
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
