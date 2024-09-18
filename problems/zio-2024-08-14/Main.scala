//> using dep dev.zio::zio:2.1.9

package scalarank.zio_2024_08_14

import zio.*

def timeout[R, E, A](eff: ZIO[R, E, A], time: Duration): ZIO[R, E, A] = for {
  effFiber <- eff.fork
  _ <- effFiber.interrupt
    .schedule(Schedule.delayed(Schedule.duration(time)))
    .fork
  r <- effFiber.join
} yield (r)

def timeout_v2[R, E, A](
    eff: ZIO[R, E, A],
    time: Duration
): ZIO[R, E, Option[A]] = for {
  effFiber <- eff.fork
  _ <- effFiber.interrupt
    .schedule(Schedule.delayed(Schedule.duration(time)))
    .fork
  r <- effFiber.join.foldCauseZIO(
    _ => ZIO.none,
    x => ZIO.some(x)
  )
  _ <- Console.printLine(r).orDie
} yield (r)

val eff: ZIO[Any, Throwable, Unit] =
  (Console.printLine("STARTING EXPENSIVE COMPUTATION!")
    *> ZIO.sleep(3.seconds)
    *> Console.printLine("FINISHED EXPENSIVE COMPUTATION!")).onInterrupt(f =>
    Console.printLine("INTERRUPTING EFFECT!").orDie
  )

object Main extends ZIOAppDefault:

  def run = timeout_v2(eff, 2.second)
