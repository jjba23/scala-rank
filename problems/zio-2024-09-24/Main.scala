//> using dep dev.zio::zio:2.1.9

package scalarank.zio_2024_09_24

import zio.*
import java.util.concurrent.TimeUnit

// var ticks = 0L

// // print the current time every 1s + increase a counter ("ticks")
// def tickingClock: UIO[Unit] = for {
//   _ <- ZIO.sleep(1.second)
//   _ <- Clock.currentTime(TimeUnit.MILLISECONDS)
//   _ <- ZIO.succeed(ticks += 1)
//   _ <- tickingClock
// } yield ()

// // print the total ticks count every 5s
// def printTicks: UIO[Unit] = for {
//   _ <- ZIO.sleep(5.seconds)
//   _ <- Console.printLine(s"TICKS: $ticks").orDie
//   _ <- printTicks
// } yield ()

// print the current time every 1s + increase a counter ("ticks")
def tickingClock(ticks: Ref[Int]): UIO[Unit] = for {
  _ <- ZIO.sleep(1.second)
  _ <- Clock.currentTime(TimeUnit.MILLISECONDS)
  _ <- ticks.update(oldV => oldV + 1)
  _ <- tickingClock(ticks)
} yield ()

// print the total ticks count every 5s
def printTicks(ticks: Ref[Int]): UIO[Unit] = for {
  _ <- ZIO.sleep(5.seconds)
  tickCount <- ticks.get
  _ <- Console.printLine(s"TICKS: $tickCount").orDie
  _ <- printTicks(ticks)
} yield ()

object Main extends ZIOAppDefault {
  def run = for {
    _ <- Console.printLine("zio-2024-09-24")
    ticks <- Ref.make[Int](0)
    _ <- (tickingClock(ticks) zipPar printTicks(ticks)).unit
  } yield ()

}
