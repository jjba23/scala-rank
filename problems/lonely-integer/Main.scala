package scalarank.lonelyinteger

def lonelyInteger(xs: Seq[Int]): Int =
  val keys = xs.toSet
  val occurs = keys.map(key => (key, xs.filter(_ == key).length))
  val lonely = occurs
    .filter { case (k, occur) =>
      occur == 1
    }
    .head
    ._1
  lonely

def printLonelyInteger(xs: Seq[Int]): Unit =
  println(s"lonelyInteger ${xs.toString}")
  println(lonelyInteger(xs))

object Main extends App:
  printLonelyInteger(Seq(1, 2, 3, 4, 3, 2, 1))
  printLonelyInteger(Seq(2, 3, 4, 3, 2))
  printLonelyInteger(Seq(3, 4, 3))
