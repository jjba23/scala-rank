package scalarank.plusminus

import java.lang.Math

def plusMinus(xs: Seq[Int]) =
  def toSixPlaces(x: Double) = Math.round(x * 1000000.0) / 1000000.0
  val positive: Double = xs.filter(x => x > 0).length
  val zero: Double = xs.filter(x => x == 0).length
  val negative: Double = xs.filter(x => x < 0).length
  (
    toSixPlaces(positive / xs.length),
    toSixPlaces(zero / xs.length),
    toSixPlaces(negative / xs.length)
  )

def printPlusMinus(xs: Seq[Int]): Unit =
  val r = plusMinus(xs)
  println(s"plusMinus ${xs.toString}")
  println("%.6f".format(r._1))
  println("%.6f".format(r._2))
  println("%.6f".format(r._3))

object Main extends App:
  printPlusMinus(Seq(1, 2, 3, 4, 5))
  printPlusMinus(Seq(5, 5, 5, 5, 5))
  printPlusMinus(Seq(1, 2, 0, -1, -3))
  printPlusMinus(Seq(5, 0, -1, -2, -3))
