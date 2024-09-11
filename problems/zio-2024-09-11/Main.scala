
// ZIO async (ZIO.async)

// 1 surface a computation running on some external thread to a ZIO
// hint: invoke callback when computation is complete
// hint2: don't wrap computation into a ZIO
def external2ZIO[A](computation: () => A)(executor: ExecutorService): Task[A] = ???

val demoExternal2ZIO = {
  val executor = Executors.newFixedThreadPool(4)
  val zio: Task[Int] = external2ZIO { () =>
    println(s"[${Thread.currentThread().getName}] computing meaning of life")
    Thread.sleep(1000)
    42
  }(executor)
}

// 2 lift a Future to a ZIO
// hint: invoke callback on future complete
def future2ZIO[A](future: => Future[A])(implicit ec: ExecutionContext): Task[A] = ???

val demoFuture2ZIO = {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec = ExecutionContext.newFixedThreadPool(4)
  val mol: Task[Int] = future2ZIO(Future{
    println(s"[${Thread.currentThread().getName}] computing meaning of life")
    Thread.sleep(1000)
    42
  })
}


// 3 never ending ZIO (not use callback?)
def neverEndingZIO[A]: UIO[A] = ???

def run = loginProgram
