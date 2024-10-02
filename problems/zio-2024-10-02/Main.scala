//> using dep dev.zio::zio:2.1.9

package scalarank.zio_2024_10_02

import zio._

object Main extends ZIOAppDefault {
  def run = for {
    _ <- Console.printLine("zio-2024-10-02")
    eggBoilTicks <- Ref.make(0)
    eggsAreBoiled <- Promise.make[Throwable, Boolean]
    _ <- ticker(eggBoilTicks, eggsAreBoiled) zipPar ringABell(eggsAreBoiled)
  } yield ()

  def ringABell(eggsAreBoiled: Promise[Throwable, Boolean]) = for {
    _ <- eggsAreBoiled.await
    _ <- ZIO.logInfo("EGGS ARE BOILED 🥚 🐔!")
  } yield ()

  def ticker(
      eggBoilTicks: Ref[Int],
      eggsAreBoiled: Promise[Throwable, Boolean]
  ): Task[Unit] = for {
    tickCount <- eggBoilTicks.get
    _ <- ZIO.logInfo(s"tick count: $tickCount")
    newTickCount <- eggBoilTicks.updateAndGet(_ + 1)
    _ <- ZIO.sleep(1.seconds)
    _ <-
      if (newTickCount == 10) (eggsAreBoiled.succeed(true))
      else (ticker(eggBoilTicks, eggsAreBoiled))
  } yield ()

}
