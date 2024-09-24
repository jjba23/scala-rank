//> using dep dev.zio::zio:2.1.9

package scalarank.zio_2024_09_11

import zio.*

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

// ZIO async (ZIO.async)

object Main extends ZIOAppDefault {
  def run = for {
    _ <- Console.printLine("demoExternal2ZIO!")
    _ <- demoExternal2ZIO
    _ <- Console.printLine("demoFuture2ZIO!")
    _ <- demoFuture2ZIO
  } yield ()

  // 1 surface a computation running on some external thread to a ZIO
  // hint: invoke callback when computation is complete
  // hint2: don't wrap computation into a ZIO
  def external2ZIO[A](
      computation: () => A
  )(executor: ExecutorService): Task[A] =
    ZIO.async[Any, Throwable, A](callback =>
      executor.execute { () =>
        val result = computation()
        callback(ZIO.succeed(result))
      }
    )

  def demoExternal2ZIO = {
    val executor = Executors.newFixedThreadPool(4)
    val zio: Task[Int] = external2ZIO { () =>
      println(s"[${Thread.currentThread().getName}] computing meaning of life")
      Thread.sleep(1000)
      42
    }(executor)
    zio
  }

// 2 lift a Future to a ZIO
// hint: invoke callback on future complete
  def future2ZIO[A](future: => Future[A])(implicit
      ec: ExecutionContext
  ): Task[A] = ZIO.async[Any, Throwable, A] { callback =>
    future.onComplete {
      case Failure(ex)    => callback(ZIO.fail(ex))
      case Success(value) => callback(ZIO.succeed(value))
    }
  }

  def demoFuture2ZIO = {
    val executor = Executors.newFixedThreadPool(4)
    implicit val ec = ExecutionContext.global
    val mol: Task[Int] = future2ZIO(Future {
      println(s"[${Thread.currentThread().getName}] computing meaning of life")
      Thread.sleep(1000)
      42
    })
    mol
  }

// 3 never ending ZIO (not use callback?)
  def neverEndingZIO[A]: UIO[A] = ZIO.never
}
