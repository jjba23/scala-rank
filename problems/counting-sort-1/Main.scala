package scalarank.countingsort1

import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration.Duration

def futureBasedCountingSort1(xs: Seq[Int]): Seq[Int] =
  val freqs = (1 to 100).map(identity).toSeq
  given ExecutionContext = ExecutionContext.global
  val sortedFuture = Future {
    freqs.map(freq => xs.filter(_ == freq).length)
  }
  println("Counting item frequency!")
  Await.result(sortedFuture, Duration.fromNanos(1000000000))

def printFutureBasedCountingSort1(xs: Seq[Int]): Unit =
  println(s"futureBasedCountingSort1 $xs")
  println(futureBasedCountingSort1(xs))

object Main extends App:
  printFutureBasedCountingSort1(Seq(1, 2, 4, 5, 6, 87, 23, 45, 12, 56))
