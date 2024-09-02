package scalarank.diagonaldifference

def diagonalDifference(xs: Seq[Seq[Int]]): Int =
  val firstDiagonalSum = xs.zipWithIndex.map { case (row, index) =>
    row(index)
  }.sum
  val secondDiagonalSum =
    xs.reverse.zipWithIndex.map { case (row, index) => row(index) }.sum

  Math.abs(firstDiagonalSum - secondDiagonalSum)

def printDiagonalDifference(xs: Seq[Seq[Int]]): Unit =
  println(s"diagonalDifference ${xs.toString}")
  println(diagonalDifference(xs))

object Main extends App:
  printDiagonalDifference(Seq(Seq(1, 2, 3), Seq(4, 5, 6), Seq(7, 8, 9)))
  printDiagonalDifference(Seq(Seq(11, 2, 4), Seq(4, 5, 6), Seq(10, 8, -12)))
