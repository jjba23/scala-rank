package scalarank.minimax

def miniMaxSum(xs: Seq[Int]): (Int, Int) =
  val combs: Seq[Seq[Int]] = xs.zipWithIndex.map((item, index) =>
    xs.zipWithIndex.filter((_, nIndex) => nIndex != index).map(x => x._1)
  )
  val sums: Seq[Int] = combs.map(_.sum).sorted

  (sums.min, sums.max)

def printMiniMaxSum(xs: Seq[Int]): Unit =
  println(s"miniMaxSum ${xs.toString} --> ${miniMaxSum(xs)}")

object Main extends App:
  printMiniMaxSum(Seq(1, 2, 3, 4, 5))
  printMiniMaxSum(Seq(5, 5, 5, 5, 5))

